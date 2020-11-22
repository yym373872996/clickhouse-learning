package person.rulo.clickhouse.learning.jdbc.original;

import person.rulo.clickhouse.learning.jdbc.original.executor.SqlExecutor;
import person.rulo.clickhouse.learning.jdbc.original.executor.type.SqlType;

/**
 * @Author rulo
 * @Date 2020/11/20 20:30
 */
public class SqlExecutorExample {

    static SqlExecutor sqlExecutor = new SqlExecutor();

    public static void main(String[] args) {
//        showDatabases();
//        dropTable();
//        createTable();
//        insertIntoTable();
        selectFromTable();
    }

    public static void showDatabases() {
        String sql = "SHOW DATABASES";
        sqlExecutor.execSqlByType(sql, SqlType.SHOW);
    }

    public static void dropTable() {
        String sql = "DROP TABLE IF EXISTS test.tag";
        sqlExecutor.execSqlByType(sql, SqlType.DROP);
    }

    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS test.tag\n" +
                "(\n" +
                "    `id` UInt64,\n" +
                "    `tag_id` UInt64,\n" +
                "    `tag_name` String,\n" +
                "    `tag_value` String,\n" +
                "    `user_list` AggregateFunction(groupBitmap, UInt64),\n" +
                "    `day_num` String\n" +
                ")\n" +
                "ENGINE = MergeTree()\n" +
                "PARTITION BY day_num\n" +
                "ORDER BY id\n" +
                "SETTINGS index_granularity = 1";
        sqlExecutor.execSqlByType(sql, SqlType.CREATE);
    }

    public static void insertIntoTable() {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO test.tag\n" +
                "(\n" +
                "    `id`,\n" +
                "    `tag_id`,\n" +
                "    `tag_name`,\n" +
                "    `tag_value`,\n" +
                "    `user_list`,\n" +
                "    `day_num`\n" +
                ")\n" +
                "VALUES\n" +
                "(\n" +
                "    1,\n" +
                "    1,\n" +
                "    'gender',\n" +
                "    'M',\n" +
                "    bitmapBuild([1, 3, 5, 7, 9]),\n" +
                "    '20201120'\n" +
                ")");
        sqlExecutor.execSqlByType(sql.toString(), SqlType.INSERT);
        sql.delete(0, sql.length());
        sql.append("INSERT INTO test.tag\n" +
                "(\n" +
                "    `id`,\n" +
                "    `tag_id`,\n" +
                "    `tag_name`,\n" +
                "    `tag_value`,\n" +
                "    `user_list`,\n" +
                "    `day_num`\n" +
                ")\n" +
                "VALUES\n" +
                "(\n" +
                "    2,\n" +
                "    2,\n" +
                "    'gender',\n" +
                "    'F',\n" +
                "    bitmapBuild([2, 4, 6, 8, 10]),\n" +
                "    '20201120'\n" +
                ")");
        sqlExecutor.execSqlByType(sql.toString(), SqlType.INSERT);
        sql.delete(0, sql.length());
        sql.append("INSERT INTO test.tag\n" +
                "(\n" +
                "    `id`,\n" +
                "    `tag_id`,\n" +
                "    `tag_name`,\n" +
                "    `tag_value`,\n" +
                "    `user_list`,\n" +
                "    `day_num`\n" +
                ")\n" +
                "VALUES\n" +
                "(\n" +
                "    3,\n" +
                "    3,\n" +
                "    'age_group',\n" +
                "    '1-10',\n" +
                "    bitmapBuild([1, 2, 3, 4, 5]),\n" +
                "    '20201120'\n" +
                ")");
        sqlExecutor.execSqlByType(sql.toString(), SqlType.INSERT);
        sql.delete(0, sql.length());
        sql.append("INSERT INTO test.tag\n" +
                "(\n" +
                "    `id`,\n" +
                "    `tag_id`,\n" +
                "    `tag_name`,\n" +
                "    `tag_value`,\n" +
                "    `user_list`,\n" +
                "    `day_num`\n" +
                ")\n" +
                "VALUES\n" +
                "(\n" +
                "    4,\n" +
                "    4,\n" +
                "    'age_group',\n" +
                "    '11-20',\n" +
                "    bitmapBuild([6, 7, 8, 9, 10]),\n" +
                "    '20201120'\n" +
                ")");
        sqlExecutor.execSqlByType(sql.toString(), SqlType.INSERT);
    }

    public static void selectFromTable() {
        String sql = "WITH\n" +
                "\n" +
                "    (\n" +
                "        SELECT user_list\n" +
                "        FROM test.tag\n" +
                "        WHERE (tag_name = 'gender') AND (tag_value = 'F') AND (day_num = '20201120')\n" +
                "    ) AS bitmap1,\n" +
                "\n" +
                "    (\n" +
                "        SELECT user_list\n" +
                "        FROM test.tag\n" +
                "        WHERE (tag_name = 'age_group') AND (tag_value = '11-20') AND (day_num = '20201120')\n" +
                "    ) AS bitmap2\n" +
                "SELECT bitmapToArray(bitmapAnd(bitmap1, bitmap2))";
        sqlExecutor.execSqlByType(sql, SqlType.SELECT);
    }
}
