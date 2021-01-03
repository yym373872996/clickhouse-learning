package person.rulo.clickhouse.learning.springboot.core.clickhouse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import person.rulo.clickhouse.learning.springboot.core.entity.type.JavaType;
import person.rulo.clickhouse.learning.springboot.core.entity.wrapper.result.ListMapWrapper;
import person.rulo.clickhouse.learning.springboot.core.entity.wrapper.result.ListRowSetWrapper;
import person.rulo.clickhouse.learning.springboot.core.entity.wrapper.result.MapWrapper;
import person.rulo.clickhouse.learning.springboot.core.strategy.ListMergingStrategy;
import person.rulo.clickhouse.learning.springboot.util.CalculatorUtil;
import person.rulo.clickhouse.learning.springboot.util.GetBeanUtil;

import javax.sql.RowSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

/**
 * @Author rulo
 * @Date 2020/11/25 20:32
 *
 * ClickHouse 集群结果集合并策略
 */
public class ClickHouseMergingStrategy extends ListMergingStrategy {

    private static Logger logger = LoggerFactory.getLogger(ClickHouseMergingStrategy.class);
    private ClickHouseJdbcHelper clickHouseJdbcHelper = GetBeanUtil.getBean(ClickHouseJdbcHelper.class);
    private CalculatorUtil calculatorUtil = GetBeanUtil.getBean(CalculatorUtil.class);

    /**
     * 拼接不同数据源返回的结果集
     * @param listRowSetWrapper
     * @return
     */
    @Override
    public ListMapWrapper unionResult(ListRowSetWrapper listRowSetWrapper) {
        List<Map<String, Object>> result = new ArrayList<>();
        List<RowSet> rowSetList = listRowSetWrapper.getContent();
        for (RowSet rowSet : rowSetList) {
            try {
                ResultSetMetaData rsmd = rowSet.getMetaData();
                while (rowSet.next()){
                    Map<String, Object> map = new HashMap<>();
                    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                        String columnName = rsmd.getColumnName(i);
                        String columnType = rsmd.getColumnTypeName(i);
                        Object columnValue = clickHouseJdbcHelper.getValueByFieldType(rowSet.getObject(rsmd.getColumnName(i)), columnType);
                        if (columnValue != null) {
                            map.put(columnName, columnValue);
                        }
                    }
                    result.add(map);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new ListMapWrapper(result);
    }

    /**
     * 聚合不同数据源返回的结果集
     * @param listRowSetWrapper
     * @return
     */
    @Override
    public MapWrapper aggregateResult(ListRowSetWrapper listRowSetWrapper) {
        Map<String, Object> result = new HashMap<>();
        int rowNum = 0;
        Object aggregation = null;
        List<RowSet> rowSetList = listRowSetWrapper.getContent();
        for (RowSet rowSet : rowSetList) {
            if (rowSet == null) break;
            try {
                ResultSetMetaData rsmd = rowSet.getMetaData();
                while (rowSet.next()){
                    rowNum++;
                    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                        String columnName = rsmd.getColumnName(i);
//                        logger.info("during aggregateResult, columnName: {}", columnName);
                        String columnType = rsmd.getColumnTypeName(i);
//                        logger.info("during aggregateResult, columnType: {}", columnType);
                        Object columnValue = clickHouseJdbcHelper.getValueByFieldType(rowSet.getObject(rsmd.getColumnName(i)), columnType);
                        if (columnValue == null) break;
                        /* 设置字段元数据中的字段类型与java类型 */
                        this.fieldMetaDataMap.get(columnName).setFieldType(columnType);
                        String fieldJavaType = clickHouseJdbcHelper.fieldTypeToJavaType(columnType);
                        this.fieldMetaDataMap.get(columnName).setFieldJavaType(fieldJavaType);
                        /* 如果该字段是聚合函数，则根据合并操作类型来进行字段值聚合 */
                        if (this.fieldMetaDataMap.get(columnName).isAggregateFunction()) {
                            JavaType javaType = JavaType.getValue(fieldJavaType);
                            switch (this.mergingOperation) {
                                case COUNT:
                                case SUM:
                                    aggregation = calculatorUtil.sum(aggregation, columnValue, javaType);
                                    break;
                                case AVG:
                                    aggregation = calculatorUtil.sum(aggregation, columnValue, javaType);
                                    if (rowNum == rowSet.getMaxRows()) {
                                        aggregation = calculatorUtil.divide(aggregation, rowNum, javaType);
                                    }
                                    break;
                                case MIN:
                                    aggregation = calculatorUtil.min(aggregation, columnValue, javaType);
                                    break;
                                case MAX:
                                    aggregation = calculatorUtil.max(aggregation, columnValue, javaType);
                                    break;
                                case AND:
                            }
                            columnValue = aggregation;
                        }
                        result.put(columnName, columnValue);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new MapWrapper(result);
    }
}
