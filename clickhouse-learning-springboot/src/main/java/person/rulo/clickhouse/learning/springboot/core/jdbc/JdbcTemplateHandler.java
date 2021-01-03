package person.rulo.clickhouse.learning.springboot.core.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

/**
 * @Author rulo
 * @Date 2021/1/1 23:14
 *
 * JDBC Template 操作类
 */
@Component
public class JdbcTemplateHandler {

    @Resource
    List<JdbcTemplate> jdbcTemplateList;

    public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
        return jdbcTemplateList.stream()
                .filter(jdbcTemplate -> dataSource.equals(jdbcTemplate.getDataSource()))
                .findAny().get();
    }

    public SqlRowSet executeQuery(DataSource dataSource, String sql) {
        return getJdbcTemplate(dataSource).queryForRowSet(sql);
    }
}
