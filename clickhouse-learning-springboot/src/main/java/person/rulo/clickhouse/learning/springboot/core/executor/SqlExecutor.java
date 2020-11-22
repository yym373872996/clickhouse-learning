package person.rulo.clickhouse.learning.springboot.core.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import person.rulo.clickhouse.learning.springboot.util.ConnectionUtil;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author rulo
 * @Date 2020/6/19 14:26
 */
@Component
public class SqlExecutor {

    private final Logger logger = LoggerFactory.getLogger(SqlExecutor.class);

    @Resource
    ConnectionUtil connectionUtil;

    public List<Map> executeQuery(DataSource dataSource, String sql) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Map> resultList = new ArrayList();
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            while (resultSet.next()){
                Map map = new HashMap();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    map.put(rsmd.getColumnName(i), resultSet.getString(rsmd.getColumnName(i)));
                }
                resultList.add(map);
            }
        } catch (SQLException e) {
            logger.error("执行SQL查询异常");
            e.printStackTrace();
        } finally {
            connectionUtil.closeResource(resultSet, statement, connection);
        }
        return resultList;
    }

}
