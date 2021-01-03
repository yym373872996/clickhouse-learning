package person.rulo.clickhouse.learning.springboot.core.entity.wrapper.datasouce;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * @Author rulo
 * @Date 2020/11/23 17:11
 *
 * 混合型数据源包装器
 */
public class MultiDataSourceWrapper extends DataSourceWrapper<Map<String, List<DataSource>>> {

    public MultiDataSourceWrapper(Map<String, List<DataSource>> content) {
        super(content);
    }
}
