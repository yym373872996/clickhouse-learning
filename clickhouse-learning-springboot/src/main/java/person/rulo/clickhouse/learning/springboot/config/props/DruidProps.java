package person.rulo.clickhouse.learning.springboot.config.props;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author rulo
 * @Date 2020/11/21 13:23
 *
 * Druid 配置属性
 */
@Component
@ConfigurationProperties(prefix = "spring.datasource.druid")
public class DruidProps {

    private String filters;
    private String testWhileIdle;

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

    public String getTestWhileIdle() {
        return testWhileIdle;
    }

    public void setTestWhileIdle(String testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }
}
