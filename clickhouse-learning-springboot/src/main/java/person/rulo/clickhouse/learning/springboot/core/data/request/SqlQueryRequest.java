package person.rulo.clickhouse.learning.springboot.core.data.request;

/**
 * @Author rulo
 * @Date 2020/11/23 17:21
 *
 * SQL 查询请求体
 */
public class SqlQueryRequest extends AbstractQueryRequest {

    private String sql;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
