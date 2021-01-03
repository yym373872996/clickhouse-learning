package person.rulo.clickhouse.learning.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author rulo
 * @Date 2021/1/1 23:06
 *
 * JDBC Template 配置
 */
@Configuration
public class JdbcTemplateConfig {

    @Resource
    List<DataSource> dataSourceList;

    @Bean
    public List<JdbcTemplate> jdbcTemplateList() {
        List<JdbcTemplate> jdbcTemplateList = new ArrayList<>();
        dataSourceList.forEach(dataSource -> {
            jdbcTemplateList.add(new JdbcTemplate(dataSource));
        });
        return jdbcTemplateList;
    }
}
