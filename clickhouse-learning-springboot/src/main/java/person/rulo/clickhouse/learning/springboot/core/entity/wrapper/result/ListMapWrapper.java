package person.rulo.clickhouse.learning.springboot.core.entity.wrapper.result;

import java.util.List;
import java.util.Map;

/**
 * @Author rulo
 * @Date 2020/11/25 19:59
 *
 * 列表型 Map 结果集包装器
 */
public class ListMapWrapper extends ResultWrapper<List<Map<String, Object>>> {

    public ListMapWrapper(List<Map<String, Object>> content) {
        super(content);
    }
}
