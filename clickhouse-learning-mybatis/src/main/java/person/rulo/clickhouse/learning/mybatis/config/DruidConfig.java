package person.rulo.clickhouse.learning.mybatis.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;

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


}
