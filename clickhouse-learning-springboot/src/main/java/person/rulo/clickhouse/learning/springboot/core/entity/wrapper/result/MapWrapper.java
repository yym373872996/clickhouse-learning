package person.rulo.clickhouse.learning.springboot.core.entity.wrapper.result;

import java.util.Map;

/**
 * @Author rulo
 * @Date 2020/11/27 10:04
 *
 * Map 型结果集包装器
 */
public class MapWrapper extends ResultWrapper<Map<String, Object>> {

    public MapWrapper(Map<String, Object> content) {
        super(content);
    }
}
