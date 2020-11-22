package person.rulo.clickhouse.learning.jdbc.original.executor;

import person.rulo.clickhouse.learning.jdbc.original.executor.type.SqlType;
import person.rulo.clickhouse.learning.jdbc.original.util.JdbcConnUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @Author rulo
 * @Date 2020/6/19 14:26
 */
public class SqlExecutor {

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;

    public void execSqlByType(String sql, SqlType type) {
        switch (type) {
            case SELECT:
            case SHOW:
                executeQuery(sql);
                break;
            case CREATE:
            case DROP:
            case INSERT:
            case ALTER:
            case UPDATE:
            case DELETE:
                executeUpdate(sql);
                break;
            default:
                execute(sql);
        }
    }

    public void executeQuery(String sql) {
        executeByOperation(sql, operation -> {
            try {
                resultSet = statement.executeQuery(sql);
                ResultSetMetaData rsmd = resultSet.getMetaData();
                List<Map> resultList = new ArrayList();
                while (resultSet.next()){
                    Map map = new HashMap();
                    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                        map.put(rsmd.getColumnName(i), resultSet.getString(rsmd.getColumnName(i)));
                    }
                    resultList.add(map);
                }
                for(Map map : resultList) {
                    System.out.println(map);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                JdbcConnUtil.getInstance().closeResource(resultSet);
            }
        });
    }

    public void executeUpdate(String sql) {
        executeByOperation(sql, operation -> {
            try {
                int rs = statement.executeUpdate(sql);
                System.out.println(rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void execute(String sql) {
        executeByOperation(sql, operation -> {
            try {
                boolean rs = statement.execute(sql);
                System.out.println(rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void executeByOperation(String sql, Consumer<String> operation) {
        try {
            connection = JdbcConnUtil.getInstance().getConnection();
            statement = connection.createStatement();
            operation.accept(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnUtil.getInstance().closeResource(statement, connection);
        }
    }

}
