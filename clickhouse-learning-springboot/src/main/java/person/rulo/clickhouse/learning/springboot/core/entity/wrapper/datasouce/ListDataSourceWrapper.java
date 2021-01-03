package person.rulo.clickhouse.learning.springboot.core.entity.wrapper.datasouce;

import javax.sql.DataSource;
import java.util.List;

/**
 * @Author rulo
 * @Date 2020/11/23 17:09
 *
 * 单种类列表型数据源包装器
 */
public class ListDataSourceWrapper extends DataSourceWrapper<List<DataSource>> {

    public ListDataSourceWrapper(List<DataSource> content) {
        super(content);
    }
}
