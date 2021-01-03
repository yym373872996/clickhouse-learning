package person.rulo.clickhouse.learning.springboot.core.queryable;

import person.rulo.clickhouse.learning.springboot.core.entity.request.SqlQueryRequest;
import person.rulo.clickhouse.learning.springboot.core.entity.response.QueryResponse;
import person.rulo.clickhouse.learning.springboot.core.entity.wrapper.result.ResultWrapper;
import person.rulo.clickhouse.learning.springboot.core.strategy.Strategy;

/**
 * @Author rulo
 * @Date 2020/11/23 17:33
 *
 * 策略化 SQL 查询抽象类
 */
public abstract class StrategicSqlQuerier implements SqlQueryable, StrategicQueryable<SqlQueryRequest> {

    /**
     * 采用策略化查询
     * @param sqlQueryRequest
     * @return
     */
    @Override
    public QueryResponse query(SqlQueryRequest sqlQueryRequest) {
        Strategy strategy = null;
        try {
            strategy = parseQuery(sqlQueryRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResultWrapper queryResult = executeQuery(strategy);
        return handleResult(queryResult, strategy);
    }
}
