package person.rulo.clickhouse.learning.springboot.core.queryable;

import person.rulo.clickhouse.learning.springboot.core.entity.response.QueryResponse;
import person.rulo.clickhouse.learning.springboot.core.entity.request.SqlQueryRequest;

/**
 * @Author rulo
 * @Date 2020/11/23 17:18
 *
 * SQL 化查询接口类
 */
public interface SqlQueryable extends Queryable<QueryResponse, SqlQueryRequest> {

}
