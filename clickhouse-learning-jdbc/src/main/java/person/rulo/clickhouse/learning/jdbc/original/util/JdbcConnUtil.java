package person.rulo.clickhouse.learning.jdbc.original.util;

import com.google.common.base.Strings;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * @Author rulo
 * @Date 2020/7/9 10:15
 */
public class JdbcConnUtil {

    private volatile static JdbcConnUtil jdbcUtil;
    private static Properties properties;
    private static String url;
    private static String username;
    private static String password;
    private static Connection connection;

    private JdbcConnUtil() {
        // 构造函数私有化，使外部调用者无法直接创建该类对象
    }

    /**
     * 单例模式，双重验证
     * @return
     */
    public static JdbcConnUtil getInstance() {
        if (jdbcUtil == null) {
            synchronized (JdbcConnUtil.class) {
                if (jdbcUtil == null) {
                    jdbcUtil = new JdbcConnUtil();
                }
            }
        }
        return jdbcUtil;
    }

    static {
        properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("database.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            Class.forName("ru.yandex.clickhouse.ClickHouseDriver");
            url = Strings.isNullOrEmpty(properties.getProperty("db.url")) ? null : properties.getProperty("db.url");
            username = Strings.isNullOrEmpty(properties.getProperty("db.username")) ? null : properties.getProperty("db.username");
            password = Strings.isNullOrEmpty(properties.getProperty("db.password")) ? null : properties.getProperty("db.password");
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("数据库驱动类加载异常");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("建立数据库连接异常");
        }
        return connection;
    }

    public void closeResource(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("ResultSet 流关闭异常");
            }
        }
    }

    public void closeResource(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Statement 流关闭异常");
            }
        }
    }

    public void closeResource(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Connection 流关闭异常");
            }
        }
    }

    public void closeResource(Statement stmt, Connection conn) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Statement 流关闭异常");
            }
        }
        closeResource(conn);
    }

    public void closeResource(ResultSet rs, Statement stmt, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("ResultSet 流关闭异常");
            }
        }
        closeResource(stmt, conn);
    }

}
