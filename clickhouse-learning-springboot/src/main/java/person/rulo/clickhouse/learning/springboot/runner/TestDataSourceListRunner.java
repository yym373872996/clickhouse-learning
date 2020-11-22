package person.rulo.clickhouse.learning.springboot.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import person.rulo.clickhouse.learning.springboot.core.executor.SqlExecutor;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author rulo
 * @Date 2020/11/22 14:49
 */
@Component
public class TestDataSourceListRunner implements ApplicationRunner {

    @Resource
    List<DataSource> dataSourceList;
    @Resource
    SqlExecutor sqlExecutor;

    public void run(ApplicationArguments args) throws Exception {
        System.out.println("datasource list size: " + dataSourceList.size());
        sqlExecutor.executeQuery(dataSourceList.get(5), "show databases");
    }
}
