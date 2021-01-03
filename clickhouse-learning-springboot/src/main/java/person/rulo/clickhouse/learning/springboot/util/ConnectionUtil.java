package person.rulo.clickhouse.learning.springboot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @Author rulo
 * @Date 2020/7/9 10:15
 *
 * JDBC 连接工具类
 */
@Component
public class ConnectionUtil {

    private final Logger logger = LoggerFactory.getLogger(ConnectionUtil.class);

    public void closeResource(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error("Connection流关闭异常");
            }
        }
    }

    public void closeResource(Statement stmt, Connection conn) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error("Statement流关闭异常");
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
                logger.error("ResultSet流关闭异常");
            }
        }
        closeResource(stmt, conn);
    }

}
