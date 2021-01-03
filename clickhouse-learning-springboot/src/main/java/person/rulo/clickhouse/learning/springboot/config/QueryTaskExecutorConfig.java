package person.rulo.clickhouse.learning.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import person.rulo.clickhouse.learning.springboot.common.RejectedPolicy;
import person.rulo.clickhouse.learning.springboot.config.props.QueryTaskExecutorProps;

import javax.annotation.Resource;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author rulo
 * @Date 2021/1/3 12:07
 *
 * 查询任务线程池配置
 */
@Configuration
@EnableAsync
public class QueryTaskExecutorConfig {

    @Resource
    QueryTaskExecutorProps queryTaskExecutorProps;

    /**
     * 使用 spring 提供的线程池类
     * @return
     */
    @Bean(name = "queryTaskExecutor")
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor queryTaskExecutor = new ThreadPoolTaskExecutor();
        queryTaskExecutor.setCorePoolSize(queryTaskExecutorProps.getCorePoolSize());
        queryTaskExecutor.setMaxPoolSize(queryTaskExecutorProps.getMaxPoolSize());
        queryTaskExecutor.setKeepAliveSeconds(queryTaskExecutorProps.getKeepAlive());
        queryTaskExecutor.setQueueCapacity(queryTaskExecutorProps.getQueueCapacity());
        queryTaskExecutor.setRejectedExecutionHandler(getRejectedExecutionHandler(queryTaskExecutorProps.getRejectedPolicy()));
        queryTaskExecutor.setAllowCoreThreadTimeOut(queryTaskExecutorProps.isAllowCoreThreadTimeout());
        queryTaskExecutor.setThreadNamePrefix(queryTaskExecutorProps.getThreadNamePrefix());
        return queryTaskExecutor;
    }

    public RejectedExecutionHandler getRejectedExecutionHandler(RejectedPolicy rejectedPolicy) {
        switch (rejectedPolicy) {
            case ABORT:
                return new ThreadPoolExecutor.AbortPolicy();
            case CALLER_RUNS:
                return new ThreadPoolExecutor.CallerRunsPolicy();
            case DISCARD:
                return new ThreadPoolExecutor.DiscardPolicy();
            case DISCARD_OLDEST:
                return new ThreadPoolExecutor.DiscardOldestPolicy();
            default:
                return null;
        }
    }
}
