package person.rulo.clickhouse.learning.jdbc.extended.util;

import ru.yandex.clickhouse.ClickHouseConnection;
import ru.yandex.clickhouse.ClickHouseDataSource;
import ru.yandex.clickhouse.ClickHouseStatement;
import ru.yandex.clickhouse.response.ClickHouseResultSet;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * @Author rulo
 * @Date 2020/7/9 10:15
 */
public class ClickHouseConnUtil {

    private volatile static ClickHouseConnUtil clickHouseConnUtil;
    private static Properties properties;
    private static String url;
    private static ClickHouseDataSource dataSource;
    private static ClickHouseConnection connection;

    private ClickHouseConnUtil() {
        // 构造函数私有化，使外部调用者无法直接创建该类对象
    }

    /**
     * 单例模式，双重验证
     * @return
     */
    public static ClickHouseConnUtil getInstance() {
        if (clickHouseConnUtil == null) {
            synchronized (ClickHouseConnUtil.class) {
                if (clickHouseConnUtil == null) {
                    clickHouseConnUtil = new ClickHouseConnUtil();
                }
            }
        }
        return clickHouseConnUtil;
    }

    static {
        properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("database.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ClickHouseConnection getConnection() {
        try {
            url = properties.getProperty("db.url");
            dataSource = new ClickHouseDataSource(url);
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("建立数据库连接异常");
        }
        return connection;
    }

    public void closeResource(ClickHouseConnection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Connection 流关闭异常");
            }
        }
    }

    public void closeResource(ClickHouseStatement stmt, ClickHouseConnection conn) {
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

    public void closeResource(ClickHouseResultSet rs, ClickHouseStatement stmt, ClickHouseConnection conn) {
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
