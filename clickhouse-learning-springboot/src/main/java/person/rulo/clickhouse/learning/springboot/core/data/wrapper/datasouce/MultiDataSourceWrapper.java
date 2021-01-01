package person.rulo.clickhouse.learning.springboot.core.data.wrapper.datasouce;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * @Author rulo
 * @Date 2020/11/23 17:11
 *
 * 混合型数据源包装器
 */
public class MultiDataSourceWrapper implements DataSourceWrapper<Map<String, List<DataSource>>> {

    private Map<String, List<DataSource>> multiDataSources;

    @Override
    public Map<String, List<DataSource>> getContent() {
        return multiDataSources;
    }

    @Override
    public void setContent(Map<String, List<DataSource>> content) {
        this.multiDataSources = content;
    }
}
