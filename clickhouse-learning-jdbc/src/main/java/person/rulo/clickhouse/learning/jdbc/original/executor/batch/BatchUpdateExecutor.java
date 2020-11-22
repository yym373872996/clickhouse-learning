package person.rulo.clickhouse.learning.jdbc.original.executor.batch;

import person.rulo.clickhouse.learning.jdbc.original.util.JdbcConnUtil;

import java.sql.*;

/**
 * @Author rulo
 * @Date 2020/7/9 12:16
 */
public class BatchUpdateExecutor {
    Connection connection = null;
    Statement statement = null;
//    ResultSet resultSet = null;

    public void batchUpdateByLoop() {
        try {
            String sql = "ALTER TABLE student UPDATE name = ? WHERE id = ?";
            connection = JdbcConnUtil.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            Long startTime = System.currentTimeMillis();
            for (int i = 1; i <= 1000; i++) {
                ((PreparedStatement) statement).setString(1, "gaga" + i);
                ((PreparedStatement) statement).setInt(2, i);
                ((PreparedStatement) statement).executeUpdate();
            }
            Long endTime = System.currentTimeMillis();
            System.out.println("用时：" + (endTime - startTime) + "ms");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnUtil.getInstance().closeResource(statement, connection);
        }
    }

    public void batchUpdateByTransaction() {
        try {
            String sql = "ALTER TABLE student UPDATE name = ? WHERE id = ?";
            connection = JdbcConnUtil.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            Long startTime = System.currentTimeMillis();
            connection.setAutoCommit(false);
            for (int i = 1; i <= 1000; i++) {
                ((PreparedStatement) statement).setString(1, "gaga" + i);
                ((PreparedStatement) statement).setInt(2, i);
                ((PreparedStatement) statement).executeUpdate();
            }
            connection.commit();
            Long endTime = System.currentTimeMillis();
            System.out.println("用时：" + (endTime - startTime) + "ms");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnUtil.getInstance().closeResource(statement, connection);
        }
    }

    /**
     * jdbc 不支持对 alter 语句的 executeBatch 操作（sql 校验不通过）
     * 意味着无法使用 executeBatch 对 clickhouse 进行批量更新
     */
    public void batchUpdateByBatchExecute() {
        try {
            String sql = "ALTER TABLE student UPDATE name = ? WHERE id = ?";
            connection = JdbcConnUtil.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            Long startTime = System.currentTimeMillis();
            connection.setAutoCommit(false);
            for (int i = 1; i <= 1000; i++) {
                ((PreparedStatement) statement).setString(1, "gaga" + i);
                ((PreparedStatement) statement).setInt(2, i);
                ((PreparedStatement) statement).addBatch();
            }
            statement.executeBatch();
            connection.commit();
            Long endTime = System.currentTimeMillis();
            System.out.println("用时：" + (endTime - startTime) + "ms");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnUtil.getInstance().closeResource(statement, connection);
        }
    }

    public void isSupportedBatchUpdates() {
        connection = JdbcConnUtil.getInstance().getConnection();
        try {
            DatabaseMetaData dbmd = connection.getMetaData();
            System.out.println(dbmd.supportsBatchUpdates());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnUtil.getInstance().closeResource(connection);
        }
    }
}
