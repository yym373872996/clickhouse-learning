package person.rulo.clickhouse.learning.springboot.core.queryable;

import person.rulo.clickhouse.learning.springboot.core.entity.request.QueryRequest;
import person.rulo.clickhouse.learning.springboot.core.entity.response.QueryResponse;
import person.rulo.clickhouse.learning.springboot.core.strategy.Strategy;
import person.rulo.clickhouse.learning.springboot.core.entity.wrapper.result.ResultWrapper;

/**
 * @Author rulo
 * @Date 2020/11/24 9:45
 *
 * 策略化查询接口类
 */
public interface StrategicQueryable<T extends QueryRequest> extends Queryable<QueryResponse, T> {

    /**
     * 解析查询请求体并生成查询策略
     * @param queryRequest
     * @return
     * @throws Exception
     */
    Strategy parseQuery(T queryRequest) throws Exception;

    /**
     * 通过查询策略获取数据库返回的结果集数据
     * @param strategy
     * @return
     */
    ResultWrapper executeQuery(Strategy strategy);

    /**
     * 对返回的结果集进行处理
     * @param resultWrapper
     * @param strategy
     * @return
     */
    QueryResponse handleResult(ResultWrapper resultWrapper, Strategy strategy);
}
