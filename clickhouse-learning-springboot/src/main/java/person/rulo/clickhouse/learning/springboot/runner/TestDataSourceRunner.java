package person.rulo.clickhouse.learning.springboot.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import person.rulo.clickhouse.learning.springboot.core.jdbc.NativeJdbcHandler;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.sql.RowSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author rulo
 * @Date 2020/11/24 19:24
 */
//@Component
public class TestDataSourceRunner implements ApplicationRunner {

    private static Logger logger = LoggerFactory.getLogger(TestDataSourceRunner.class);

    @Resource
    DataSource dataSource;
    @Resource
    NativeJdbcHandler nativeJdbcHandler;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        RowSet rowSet = nativeJdbcHandler.executeQuery(dataSource, "show databases");
        ResultSetMetaData rsmd = rowSet.getMetaData();
        List<Map<String, String>> mapList = new ArrayList<>();
        while (rowSet.next()){
            Map<String, String> map = new HashMap<>();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                map.put(rsmd.getColumnName(i), rowSet.getString(rsmd.getColumnName(i)));
            }
            mapList.add(map);
        }
        logger.info("databases: {}", mapList);
    }
}
