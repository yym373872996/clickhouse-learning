package person.rulo.clickhouse.learning.springboot.core.clickhouse;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import person.rulo.clickhouse.learning.springboot.core.parser.SqlParseHelper;
import person.rulo.clickhouse.learning.springboot.core.queryable.StrategicSqlQuerier;
import person.rulo.clickhouse.learning.springboot.core.entity.request.SqlQueryRequest;
import person.rulo.clickhouse.learning.springboot.core.entity.response.QueryResponse;
import person.rulo.clickhouse.learning.springboot.core.strategy.FieldMetaData;
import person.rulo.clickhouse.learning.springboot.core.strategy.Strategy;
import person.rulo.clickhouse.learning.springboot.core.entity.type.MergingOperation;
import person.rulo.clickhouse.learning.springboot.core.entity.wrapper.datasouce.ListDataSourceWrapper;
import person.rulo.clickhouse.learning.springboot.core.entity.wrapper.result.ListRowSetWrapper;
import person.rulo.clickhouse.learning.springboot.core.entity.wrapper.result.ResultWrapper;
import person.rulo.clickhouse.learning.springboot.core.proxy.QueryTaskProxy;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.sql.RowSet;
import java.util.*;
import java.util.concurrent.*;

/**
 * @Author rulo
 * @Date 2020/11/24 9:56
 *
 * ClickHouse 集群查询实现
 */
@Component
@EnableAsync
public class ClickHouseClusterQuerier extends StrategicSqlQuerier {

    private static Logger logger = LoggerFactory.getLogger(ClickHouseClusterQuerier.class);

    @Resource
    ClickHouseSqlParser clickHouseSqlParser;
    @Resource
    SqlParseHelper sqlParseHelper;
    @Resource
    QueryTaskProxy queryTaskProxy;

    /**
     * 解析查询请求并生成适用于 ClickHouse 集群数据源的结果合并策略
     * @param sqlQueryRequest
     * @return
     * @throws Exception
     */
    @Override
    public Strategy parseQuery(SqlQueryRequest sqlQueryRequest) throws Exception {
        ClickHouseMergingStrategy strategy = new ClickHouseMergingStrategy();
        /* 采用 Druid 的 ClickHouse SQL 解析器解析 SQL */
        SQLStatement sqlStatement = clickHouseSqlParser.parseSql(sqlQueryRequest.getSql());
        /* 验证是否为查询语句 */
        if (!(sqlStatement instanceof SQLSelectStatement)) {
            throw new Exception("only support select sql statement");
        }
        /* 获取查询字段列表 */
        SQLSelectStatement selectStatement = (SQLSelectStatement) sqlStatement;
        SQLSelectQueryBlock selectBlock = selectStatement.getSelect().getQueryBlock();
        List<SQLSelectItem> selectItemList = selectBlock.getSelectList();
        /* 遍历字段列表并生成字段元数据信息 */
        Map<String, FieldMetaData> fieldMetaDataMap = new HashMap<>();
        selectItemList.forEach(selectItem -> {
            FieldMetaData fieldMetaData = new FieldMetaData();
            String fieldName = selectItem.getExpr().toString();
            fieldMetaData.setFieldName(fieldName);
            String fieldAlias = selectItem.getAlias();
            fieldMetaData.setFieldAlias(fieldAlias);
            String fieldFunction = sqlParseHelper.getFieldFunction(fieldMetaData.getFieldName());
            if (!Strings.isNullOrEmpty(fieldFunction)) {
                fieldMetaData.setFieldFunction(fieldFunction);
            }
            /* 优先选取字段别名作为map的key，如没有别名则选取字段名 */
            if (!Strings.isNullOrEmpty(fieldAlias)) {
                fieldMetaDataMap.put(fieldAlias, fieldMetaData);
            } else {
                fieldMetaDataMap.put(fieldName, fieldMetaData);
            }
        });
        /* 组装查询策略 */
        strategy.setListDataSourceWrapper((ListDataSourceWrapper) sqlQueryRequest.getDataSourceWrapper());
        strategy.setSqlStatement(sqlStatement);
        strategy.setFieldMetaDataMap(fieldMetaDataMap);
        // 推断合并操作类型
        strategy.setMergingOperation(inferMergingOperation(fieldMetaDataMap));
        return strategy;
    }

    /**
     * 查询所有数据源，将查询结果打包返回
     * @param strategy
     * @return
     */
    @Override
    public ResultWrapper executeQuery(Strategy strategy) {
        ClickHouseMergingStrategy clickHouseMergingStrategy = (ClickHouseMergingStrategy) strategy;
        List<RowSet> rowSetList = new ArrayList<>();
        ListDataSourceWrapper listDataSourceWrapper = clickHouseMergingStrategy.getListDataSourceWrapper();
        List<DataSource> dataSourceList = listDataSourceWrapper.getContent();
        String sql = clickHouseMergingStrategy.getSqlStatement().toString();
        List<Future<RowSet>> futureList = new ArrayList<>();
        dataSourceList.forEach(dataSource -> {
            futureList.add(queryTaskProxy.queryAsync(dataSource, sql));
        });
        futureList.forEach(future -> {
            try {
                rowSetList.add(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        return new ListRowSetWrapper(rowSetList);
    }

    /**
     * 合并从多个数据源返回的查询结果并封装成响应体返回
     * @param resultWrapper
     * @param strategy
     * @return
     */
    @Override
    public QueryResponse handleResult(ResultWrapper resultWrapper, Strategy strategy) {
        QueryResponse queryResponse = new QueryResponse();
        ListRowSetWrapper listRowSetWrapper = (ListRowSetWrapper) resultWrapper;
        ClickHouseMergingStrategy clickHouseMergingStrategy = (ClickHouseMergingStrategy) strategy;
        resultWrapper = clickHouseMergingStrategy.mergeResult(listRowSetWrapper);
        queryResponse.setResultWrapper(resultWrapper);
        return queryResponse;
    }

    /**
     * 根据字段元数据信息推断合并操作
     * @param fieldMetaDataMap
     * @return
     */
    public MergingOperation inferMergingOperation(Map<String, FieldMetaData> fieldMetaDataMap) {
        // 合并操作默认为 union
        MergingOperation mergingOperation = MergingOperation.UNION;
        /* 遍历所有字段元数据，如果该字段包含函数操作，根据函数类型推断聚合操作 */
        for (FieldMetaData fieldMetaData : fieldMetaDataMap.values()) {
            if (!Strings.isNullOrEmpty(fieldMetaData.getFieldFunction())) {
                String fieldFunction = fieldMetaData.getFieldFunction();
                ClickHouseFunction clickHouseFunction = ClickHouseFunction.getValue(fieldFunction);
                // 未能匹配到的函数类型默认设置为 unknown
                if (clickHouseFunction == null) clickHouseFunction = ClickHouseFunction.UNKNOWN;
                /* 根据函数类型匹配合并操作类型 */
                switch (clickHouseFunction) {
                    case UNKNOWN:
                    case BITMAP_TO_ARRAY:
                    case BITMAP_AND:
                    case BITMAP_OR:
                    case BITMAP_XOR:
                    case BITMAP_ANDNOT:
                        mergingOperation = MergingOperation.UNION;
                        break;
                    case COUNT:
                    case BITMAP_CARDINALITY:
                    case BITMAP_AND_CARDINALITY:
                    case BITMAP_OR_CARDINALITY:
                    case BITMAP_XOR_CARDINALITY:
                    case BITMAP_ANDNOT_CARDINALITY:
                        mergingOperation = MergingOperation.COUNT;
                        break;
                    case SUM:
                        mergingOperation = MergingOperation.SUM;
                        break;
                    case MIN:
                        mergingOperation = MergingOperation.MIN;
                        break;
                    case MAX:
                        mergingOperation = MergingOperation.MAX;
                        break;
                    case AVG:
                        mergingOperation = MergingOperation.AVG;
                        break;
                    case BITMAP_CONTAINS:
                        mergingOperation = MergingOperation.AND;
                }
                /* 如果是聚合函数，则直接确定聚合操作的类型，不再继续推断（一个 select list 中只支持一个聚合函数） */
                if (clickHouseFunction.isAggregateFunction()) {
                    fieldMetaData.setAggregateFunction(true);
                    break;
                }
            }
        }
        return mergingOperation;
    }
}
