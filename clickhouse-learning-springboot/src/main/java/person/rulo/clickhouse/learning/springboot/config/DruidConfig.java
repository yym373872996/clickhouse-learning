package person.rulo.clickhouse.learning.springboot.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author rulo
 * @Date 2020/11/21 13:19
 */
@Configuration
public class DruidConfig {

    @Resource
    private JdbcParamConfig jdbcParamConfig;

    @Bean
    public DataSource dataSource() {
        // 新版的 druid 需要采用 DruidDataSourceBuilder 来创建 DruidDataSource 而不能用 new，否则无法正常初始化 Filter
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        dataSource.setUrl(jdbcParamConfig.getUrl());
        dataSource.setDriverClassName(jdbcParamConfig.getDriver());
        dataSource.setUsername(jdbcParamConfig.getUsername());
        dataSource.setPassword(jdbcParamConfig.getPassword());
        dataSource.setInitialSize(jdbcParamConfig.getInitialSize());
        dataSource.setMinIdle(jdbcParamConfig.getMinIdle());
        dataSource.setMaxActive(jdbcParamConfig.getMaxActive());
        dataSource.setMaxWait(jdbcParamConfig.getMaxWait());
        return dataSource;
    }

    @Bean
    public List<DataSource> dataSourceList() {
        List<DataSource> dataSourceList = new ArrayList<DataSource>();
        for (int i =0; i < 10; i++) {
            dataSourceList.add(dataSource());
        }
        return dataSourceList;
    }


}
