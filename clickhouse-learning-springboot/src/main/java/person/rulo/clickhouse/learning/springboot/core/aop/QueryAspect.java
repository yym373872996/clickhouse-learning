package person.rulo.clickhouse.learning.springboot.core.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import person.rulo.clickhouse.learning.springboot.common.ResponseCode;
import person.rulo.clickhouse.learning.springboot.core.entity.response.QueryResponse;
import person.rulo.clickhouse.learning.springboot.util.ExceptionUtil;

import javax.annotation.Resource;

/**
 * @Author rulo
 * @Date 2021/1/2 14:43
 */
@Aspect
@Component
public class QueryAspect {

    private static Logger logger = LoggerFactory.getLogger(QueryAspect.class);

    @Resource
    ExceptionUtil exceptionUtil;

    @Around("execution(person.rulo.clickhouse.learning.springboot.core.entity.response.QueryResponse person.rulo.clickhouse.learning.springboot.core.queryable.Queryable.query(..))")
    public QueryResponse aroundQuery(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        QueryResponse response = new QueryResponse();
        try {
            response = (QueryResponse) proceedingJoinPoint.proceed();
            response.setCode(ResponseCode.SUCCESS.getCode());
        } catch (Exception e) {
            response.setCode(ResponseCode.ERROR.getCode());
            response.setMessage(exceptionUtil.getWholeException(e));
            logger.error("an error occurred while querying:\n {}", response.getMessage());
        }
        long endTime = System.currentTimeMillis();
        response.setCostTime(endTime - startTime);
        return response;
    }
}
