package person.rulo.clickhouse.learning.jdbc.original;

import person.rulo.clickhouse.learning.jdbc.original.executor.SqlExecutor;
import person.rulo.clickhouse.learning.jdbc.original.executor.batch.BatchInsertExecutor;
import person.rulo.clickhouse.learning.jdbc.original.executor.batch.BatchUpdateExecutor;
import person.rulo.clickhouse.learning.jdbc.original.executor.type.SqlType;

/**
 * @Author rulo
 * @Date 2020/11/20 21:56
 */
public class BatchExecutorExample {

    static SqlExecutor sqlExecutor = new SqlExecutor();
    static BatchInsertExecutor batchInsertExecutor = new BatchInsertExecutor();
    static BatchUpdateExecutor batchUpdateExecutor = new BatchUpdateExecutor();

    public static void main(String[] args) {
        dropTable();
        createTable();
        batchInsertExecutor.batchInsertByLoop();
        batchInsertExecutor.batchInsertByTransaction();
        batchInsertExecutor.batchInsertByExecuteBatch();
        batchUpdateExecutor.batchUpdateByLoop();
        batchUpdateExecutor.batchUpdateByTransaction();
        // 不支持的方法
        batchUpdateExecutor.batchUpdateByBatchExecute();
        batchUpdateExecutor.isSupportedBatchUpdates();
    }

    public static void dropTable() {
        String sql = "DROP TABLE IF EXISTS test.student";
        sqlExecutor.execSqlByType(sql, SqlType.DROP);
    }

    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS test.student\n" +
                "(\n" +
                "    `id` UInt64,\n" +
                "    `name` String\n" +
                ")\n" +
                "ENGINE = MergeTree()\n" +
                "ORDER BY id";
        sqlExecutor.execSqlByType(sql, SqlType.CREATE);
    }
}
