package person.rulo.clickhouse.learning.springboot.core.entity.wrapper.result;

import javax.sql.RowSet;
import java.util.List;
import java.util.Map;

/**
 * @Author rulo
 * @Date 2020/11/24 16:51
 *
 * 列表型 RowSet 结果集包装器
 */
public class ListRowSetWrapper extends ResultWrapper<List<RowSet>> {

    public ListRowSetWrapper(List<RowSet> content) {
        super(content);
    }
}
