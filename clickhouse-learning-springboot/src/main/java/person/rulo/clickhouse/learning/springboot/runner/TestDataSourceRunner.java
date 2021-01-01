package person.rulo.clickhouse.learning.springboot.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import person.rulo.clickhouse.learning.springboot.core.executor.SqlExecutor;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.sql.RowSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author rulo
 * @Date 2020/11/24 19:24
 */
//@Component
public class TestDataSourceRunner implements ApplicationRunner {

    @Resource
    DataSource dataSource;
    @Resource
    SqlExecutor sqlExecutor;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        RowSet rowSet = sqlExecutor.executeQuery(dataSource, "show databases");
        ResultSetMetaData rsmd = rowSet.getMetaData();
        while (rowSet.next()){
            Map<String, String> map = new HashMap<>();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                map.put(rsmd.getColumnName(i), rowSet.getString(rsmd.getColumnName(i)));
            }
            System.out.println(map);
        }
    }
}
