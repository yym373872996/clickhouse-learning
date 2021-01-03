package person.rulo.clickhouse.learning.springboot.core.entity.wrapper.datasouce;

import javax.sql.DataSource;

/**
 * @Author rulo
 * @Date 2020/11/23 17:07
 *
 * 单数据源包装器
 */
public class SingleDataSourceWrapper extends DataSourceWrapper<DataSource> {

    public SingleDataSourceWrapper(DataSource content) {
        super(content);
    }
}
