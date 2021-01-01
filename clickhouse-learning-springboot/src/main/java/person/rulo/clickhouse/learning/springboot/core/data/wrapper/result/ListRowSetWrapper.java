package person.rulo.clickhouse.learning.springboot.core.data.wrapper.result;

import javax.sql.RowSet;
import java.util.List;

/**
 * @Author rulo
 * @Date 2020/11/24 16:51
 *
 * RowSet 列表型结果集包装器
 */
public class ListRowSetWrapper implements ResultWrapper<List<RowSet>> {

    private List<RowSet> rowSetList;

    public ListRowSetWrapper(List<RowSet> rowSetList) {
        this.rowSetList = rowSetList;
    }

    @Override
    public List<RowSet> getContent() {
        return rowSetList;
    }

    @Override
    public void setContent(List<RowSet> content) {
        this.rowSetList = content;
    }
}
