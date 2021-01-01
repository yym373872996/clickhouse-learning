package person.rulo.clickhouse.learning.springboot.core.data.wrapper.datasouce;

import javax.sql.DataSource;
import java.util.List;

/**
 * @Author rulo
 * @Date 2020/11/23 17:09
 *
 * 单种类列表型数据源包装器
 */
public class ListDataSourceWrapper implements DataSourceWrapper<List<DataSource>> {

    private List<DataSource> dataSourceList;

    @Override
    public List<DataSource> getContent() {
        return dataSourceList;
    }

    @Override
    public void setContent(List<DataSource> content) {
        this.dataSourceList = content;
    }
}
