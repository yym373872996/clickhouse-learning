package person.rulo.clickhouse.learning.springboot.common;

/**
 * @Author rulo
 * @Date 2021/1/3 14:01
 *
 * 线程池拒绝策略
 */
public enum RejectedPolicy {
    ABORT,
    CALLER_RUNS,
    DISCARD,
    DISCARD_OLDEST
}
