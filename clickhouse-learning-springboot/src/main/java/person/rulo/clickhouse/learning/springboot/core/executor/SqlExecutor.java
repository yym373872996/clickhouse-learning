package person.rulo.clickhouse.learning.springboot.core.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import person.rulo.clickhouse.learning.springboot.util.ConnectionUtil;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @Author rulo
 * @Date 2020/11/24 20:35
 *
 * SQL 执行器
 */
@Component
public class SqlExecutor {
    private final Logger logger = LoggerFactory.getLogger(SqlExecutor.class);

    @Resource
    ConnectionUtil connectionUtil;

    public RowSet executeQuery(DataSource dataSource, String sql) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        CachedRowSet rowSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            /* 使用 RowSet 离线存储查询结果 */
            rowSet = RowSetProvider.newFactory().createCachedRowSet();
            rowSet.populate(resultSet);
        } catch (SQLException e) {
            logger.error("执行SQL查询异常");
            e.printStackTrace();
        } finally {
            connectionUtil.closeResource(resultSet, statement, connection);
        }
        return rowSet;
    }
}
