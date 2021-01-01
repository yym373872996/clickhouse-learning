package person.rulo.clickhouse.learning.springboot.core.data.wrapper.result;

import java.util.List;
import java.util.Map;

/**
 * @Author rulo
 * @Date 2020/11/25 19:59
 *
 * Map 列表型结果集包装器
 */
public class ListMapWrapper implements ResultWrapper<List<Map<String, Object>>> {

    private List<Map<String, Object>> mapList;

    public ListMapWrapper(List<Map<String, Object>> mapList) {
        this.mapList = mapList;
    }

    @Override
    public List<Map<String, Object>> getContent() {
        return mapList;
    }

    @Override
    public void setContent(List<Map<String, Object>> content) {
        this.mapList = content;
    }
}
