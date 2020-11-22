package person.rulo.clickhouse.learning.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
 * @Date 2020/11/22 16:08
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    DataSource dataSource;
    @Resource
    SqlExecutor sqlExecutor;

    @RequestMapping("/execsql")
    public void selectList() {
        sqlExecutor.executeQuery(dataSource, "show databases");
    }
}
