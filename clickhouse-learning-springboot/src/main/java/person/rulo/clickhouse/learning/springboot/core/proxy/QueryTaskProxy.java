package person.rulo.clickhouse.learning.springboot.core.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import person.rulo.clickhouse.learning.springboot.core.jdbc.NativeJdbcHandler;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.sql.RowSet;
import java.util.concurrent.Future;

/**
 * @Author rulo
 * @Date 2021/1/3 17:22
 *
 * 查询任务执行代理
 */
@Component
public class QueryTaskProxy {

    private static Logger logger = LoggerFactory.getLogger(QueryTaskProxy.class);

    @Resource
    NativeJdbcHandler nativeJdbcHandler;

    /**
     * 异步执行查询任务
     * @param dataSource
     * @param sql
     * @return
     */
    @Async("queryTaskExecutor")
    public Future<RowSet> queryAsync(DataSource dataSource, String sql) {
        logger.info("start execution: {}", Thread.currentThread().getName());
        RowSet rowSet = null;
        try{
//            Thread.sleep(1000);
            rowSet = nativeJdbcHandler.executeQuery(dataSource, sql);
        }catch(Exception e){
            e.printStackTrace();
        }
        logger.info("end execution: {}", Thread.currentThread().getName());
        return new AsyncResult<>(rowSet);
    }

    /**
     * 同步执行查询
     * @param dataSource
     * @param sql
     * @return
     */
    public RowSet querySync(DataSource dataSource, String sql) {
        return nativeJdbcHandler.executeQuery(dataSource, sql);
    }
}
