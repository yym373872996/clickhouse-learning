package person.rulo.clickhouse.learning.springboot.core.entity.request;

import person.rulo.clickhouse.learning.springboot.core.entity.wrapper.datasouce.DataSourceWrapper;

/**
 * @Author rulo
 * @Date 2020/11/23 16:01
 *
 * 查询请求体抽象类
 */
public class AbstractQueryRequest implements QueryRequest {

    // 数据源包装器
    private DataSourceWrapper dataSourceWrapper;

    public AbstractQueryRequest() {
    }

    public AbstractQueryRequest(DataSourceWrapper dataSourceWrapper) {
        this.dataSourceWrapper = dataSourceWrapper;
    }

    public DataSourceWrapper getDataSourceWrapper() {
        return dataSourceWrapper;
    }

    public void setDataSourceWrapper(DataSourceWrapper dataSourceWrapper) {
        this.dataSourceWrapper = dataSourceWrapper;
    }
}
