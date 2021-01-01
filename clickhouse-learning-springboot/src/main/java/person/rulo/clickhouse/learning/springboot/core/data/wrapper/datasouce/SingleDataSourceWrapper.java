package person.rulo.clickhouse.learning.springboot.core.data.wrapper.datasouce;

import javax.sql.DataSource;

/**
 * @Author rulo
 * @Date 2020/11/23 17:07
 *
 * 单数据源包装器
 */
public class SingleDataSourceWrapper implements DataSourceWrapper<DataSource> {

    private DataSource dataSource;

    @Override
    public DataSource getContent() {
        return dataSource;
    }

    @Override
    public void setContent(DataSource content) {
        this.dataSource = content;
    }
}
