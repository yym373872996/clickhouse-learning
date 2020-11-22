package person.rulo.clickhouse.learning.jdbc.extended;

import person.rulo.clickhouse.learning.jdbc.extended.executor.ClickHouseExecutor;

/**
 * @Author rulo
 * @Date 2020/11/20 22:12
 */
public class ClickHouseExecutorExample {

    static ClickHouseExecutor executor = new ClickHouseExecutor();

    public static void main(String[] args) {
        executor.insertByCallback();
        executor.insertConfigurable();
    }
}
