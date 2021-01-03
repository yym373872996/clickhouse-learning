package person.rulo.clickhouse.learning.springboot.core.strategy;

import com.alibaba.druid.sql.ast.SQLStatement;
import person.rulo.clickhouse.learning.springboot.core.entity.type.MergingOperation;
import person.rulo.clickhouse.learning.springboot.core.entity.wrapper.datasouce.ListDataSourceWrapper;
import person.rulo.clickhouse.learning.springboot.core.entity.wrapper.result.ListMapWrapper;
import person.rulo.clickhouse.learning.springboot.core.entity.wrapper.result.ListRowSetWrapper;
import person.rulo.clickhouse.learning.springboot.core.entity.wrapper.result.MapWrapper;
import person.rulo.clickhouse.learning.springboot.core.entity.wrapper.result.ResultWrapper;

import java.util.Map;

/**
 * @Author rulo
 * @Date 2020/11/25 20:32
 *
 * 列表型数据源结果合并策略
 */
public abstract class ListMergingStrategy implements Strategy {

    // 列表型数据源包装器
    protected ListDataSourceWrapper listDataSourceWrapper;
    // 解析后的 SQL 语句对象
    protected SQLStatement sqlStatement;
    // 字段元数据映射表
    protected Map<String, FieldMetaData> fieldMetaDataMap;
    // 合并操作类型
    protected MergingOperation mergingOperation;

    /**
     * 合并查询结果
     * @param listRowSetWrapper
     * @return
     */
    public ResultWrapper mergeResult(ListRowSetWrapper listRowSetWrapper) {
        ResultWrapper resultWrapper = null;
        switch (mergingOperation) {
            case UNION:
                resultWrapper = unionResult(listRowSetWrapper);
                break;
            case COUNT:
            case SUM:
            case MIN:
            case MAX:
            case AVG:
            case AND:
                resultWrapper = aggregateResult(listRowSetWrapper);
        }
        return resultWrapper;
    }

    /**
     * 堆叠结果集
     * @param listRowSetWrapper
     * @return
     */
    public abstract ListMapWrapper unionResult(ListRowSetWrapper listRowSetWrapper);

    /**
     * 聚合结果
     * @param listRowSetWrapper
     * @return
     */
    public abstract MapWrapper aggregateResult(ListRowSetWrapper listRowSetWrapper);

    public ListDataSourceWrapper getListDataSourceWrapper() {
        return listDataSourceWrapper;
    }

    public void setListDataSourceWrapper(ListDataSourceWrapper listDataSourceWrapper) {
        this.listDataSourceWrapper = listDataSourceWrapper;
    }

    public SQLStatement getSqlStatement() {
        return sqlStatement;
    }

    public void setSqlStatement(SQLStatement sqlStatement) {
        this.sqlStatement = sqlStatement;
    }

    public Map<String, FieldMetaData> getFieldMetaDataMap() {
        return fieldMetaDataMap;
    }

    public void setFieldMetaDataMap(Map<String, FieldMetaData> fieldMetaDataMap) {
        this.fieldMetaDataMap = fieldMetaDataMap;
    }

    public MergingOperation getMergingOperation() {
        return mergingOperation;
    }

    public void setMergingOperation(MergingOperation mergingOperation) {
        this.mergingOperation = mergingOperation;
    }

}
