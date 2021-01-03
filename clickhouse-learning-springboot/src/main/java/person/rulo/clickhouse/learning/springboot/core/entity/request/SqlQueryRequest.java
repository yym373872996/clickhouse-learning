package person.rulo.clickhouse.learning.springboot.core.entity.request;

import person.rulo.clickhouse.learning.springboot.core.entity.wrapper.datasouce.DataSourceWrapper;

/**
 * @Author rulo
 * @Date 2020/11/23 17:21
 *
 * SQL 查询请求体
 */
public class SqlQueryRequest extends AbstractQueryRequest {

    private String sql;

    public SqlQueryRequest() {
    }

    public SqlQueryRequest(DataSourceWrapper dataSourceWrapper, String sql) {
        super(dataSourceWrapper);
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
