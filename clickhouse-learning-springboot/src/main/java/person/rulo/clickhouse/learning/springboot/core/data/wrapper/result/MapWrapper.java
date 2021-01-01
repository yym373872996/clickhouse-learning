package person.rulo.clickhouse.learning.springboot.core.data.wrapper.result;

import java.util.Map;

/**
 * @Author rulo
 * @Date 2020/11/27 10:04
 *
 * Map 型结果集包装器
 */
public class MapWrapper implements ResultWrapper<Map<String, Object>> {

    private Map<String, Object> map;

    public MapWrapper(Map<String, Object> map) {
        this.map = map;
    }

    @Override
    public Map<String, Object> getContent() {
        return map;
    }

    @Override
    public void setContent(Map<String, Object> content) {
        this.map = content;
    }
}
