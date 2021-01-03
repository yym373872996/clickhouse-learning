package person.rulo.clickhouse.learning.springboot.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.assertj.core.util.Strings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import person.rulo.clickhouse.learning.springboot.common.CommonConsts;
import person.rulo.clickhouse.learning.springboot.config.props.ClickHouseJdbcProps;
import person.rulo.clickhouse.learning.springboot.config.props.DruidProps;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author rulo
 * @Date 2020/11/21 13:19
 *
 * ClickHouse Druid 连接池配置
 */
@Configuration
public class ClickHouseDataSourceConfig {

    @Resource
    ClickHouseJdbcProps clickHouseJdbcConfig;
    @Resource
    DruidProps druidProps;

    @Bean
    public List<DataSource> dataSourceList() {
        List<DataSource> dataSourceList = new ArrayList<>();
        String[] nodeList = clickHouseJdbcConfig.getNodeList().split(CommonConsts.COMMA);
        Arrays.asList(nodeList).forEach(nodeInfo -> {
            dataSourceList.add(buildDataSource(nodeInfo));
        });
        return dataSourceList;
    }

    /**
     * Druid 需要至少返回一个 DataSource 实例才能成功初始化
     * @return
     */
    @Bean
    public DataSource dataSource() {
        DataSource dataSource = null;
        String[] nodeList = clickHouseJdbcConfig.getNodeList().split(CommonConsts.COMMA);
        for (String nodeInfo : nodeList) {
            dataSource = buildDataSource(nodeInfo);
            if (dataSource != null) {
                break;
            }
        }
        return dataSource;
    }

    public DataSource buildDataSource(String nodeInfo) {
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        String jdbcUrl = Strings.concat(
                CommonConsts.JDBC_PREFIX,
                clickHouseJdbcConfig.getDbType(),
                CommonConsts.COLON,
                CommonConsts.DOUBLE_SLASH,
                nodeInfo,
                CommonConsts.SLASH,
                clickHouseJdbcConfig.getDatabase());
        dataSource.setUrl(jdbcUrl);
        dataSource.setDriverClassName(clickHouseJdbcConfig.getDriver());
        dataSource.setUsername(clickHouseJdbcConfig.getUsername());
        dataSource.setPassword(clickHouseJdbcConfig.getPassword());
        String nodeName = nodeInfo.replace(CommonConsts.COLON, CommonConsts.HYPHEN);
        dataSource.setName(nodeName);
        dataSource.setInitialSize(clickHouseJdbcConfig.getInitialSize());
        dataSource.setMinIdle(clickHouseJdbcConfig.getMinIdle());
        dataSource.setMaxActive(clickHouseJdbcConfig.getMaxActive());
        dataSource.setMaxWait(clickHouseJdbcConfig.getMaxWait());
        dataSource.setTestWhileIdle(Boolean.getBoolean(druidProps.getTestWhileIdle()));
        try {
            dataSource.setFilters(druidProps.getFilters());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataSource;
    }



}
