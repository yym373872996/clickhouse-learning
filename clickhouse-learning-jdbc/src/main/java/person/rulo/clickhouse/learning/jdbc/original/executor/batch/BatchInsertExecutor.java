package person.rulo.clickhouse.learning.jdbc.original.executor.batch;

import person.rulo.clickhouse.learning.jdbc.original.util.JdbcConnUtil;

import java.sql.*;

/**
 * @Author rulo
 * @Date 2020/7/9 9:24
 */
public class BatchInsertExecutor {

    Connection connection = null;
    Statement statement = null;
//    ResultSet resultSet = null;

    public void batchInsertByLoop() {
        try {
            String sql = "INSERT INTO student VALUES(?,?)";
            connection = JdbcConnUtil.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            Long startTime = System.currentTimeMillis();
            for (int i = 1; i <= 10000; i++) {
                ((PreparedStatement) statement).setInt(1, i);
                ((PreparedStatement) statement).setString(2, "rua" + i);
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

    public void batchInsertByTransaction() {
        try {
            String sql = "INSERT INTO student VALUES(?,?)";
            connection = JdbcConnUtil.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            Long startTime = System.currentTimeMillis();
            connection.setAutoCommit(false);
            for (int i = 1; i <= 10000; i++) {
                ((PreparedStatement) statement).setInt(1, i);
                ((PreparedStatement) statement).setString(2, "rua" + i);
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

    public void batchInsertByExecuteBatch() {
        try {
            String sql = "INSERT INTO student VALUES(?,?)";
            connection = JdbcConnUtil.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            Long startTime = System.currentTimeMillis();
            connection.setAutoCommit(false);
            for (int i = 1; i <= 1000000; i++) {
                ((PreparedStatement) statement).setInt(1, i);
                ((PreparedStatement) statement).setString(2, "rua" + i);
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

}
