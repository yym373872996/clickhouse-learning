package person.rulo.clickhouse.learning.springboot.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import person.rulo.clickhouse.learning.springboot.core.clickhouse.ClickHouseClusterQuerier;
import person.rulo.clickhouse.learning.springboot.core.data.request.SqlQueryRequest;
import person.rulo.clickhouse.learning.springboot.core.data.wrapper.datasouce.ListDataSourceWrapper;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author rulo
 * @Date 2020/11/24 19:24
 */
@Component
public class TestQueryRunner implements ApplicationRunner {

    @Resource
    List<DataSource> dataSourceList;
    @Resource
    ClickHouseClusterQuerier querier;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<String> sqlList = new ArrayList<>();
        sqlList.add(getSqlUserGroup());
//        sqlList.add(getSqlUserGroupCount());
//        sqlList.add(getSqlUserProfiles());
//        sqlList.add(getSqlUserInsights());
        sqlList.forEach(sql -> {
            ListDataSourceWrapper dataSourceWrapper = new ListDataSourceWrapper();
            dataSourceWrapper.setContent(dataSourceList);
            SqlQueryRequest request = new SqlQueryRequest();
            request.setDataSourceWrapper(dataSourceWrapper);
            request.setSql(sql);
            querier.query(request);
        });
    }

    public String getSqlUserProfiles() {
        String sql = "SELECT \n" +
                "    labelname, \n" +
                "    labelvalue\n" +
                "FROM label_string_granularity_1\n" +
                "WHERE dayno = '2020-11-25' AND bitmapContains(uv, toUInt32(5)) = 1\n" +
                "LIMIT 3\n" +
                "settings merge_tree_min_rows_for_concurrent_read=100";
        return sql;
    }

    public String getSqlUserGroup() {
        String sql = "WITH \n" +
                "    (\n" +
                "        SELECT groupBitmapMergeState(uv) AS user1\n" +
                "        FROM label_string_granularity_1\n" +
                "        WHERE dayno = '2020-11-25' AND (labelname = 'gender') AND (labelvalue = 'M')\n" +
                "    ) AS bitmap1,\n" +
                "    (\n" +
                "        SELECT uv AS user2\n" +
                "        FROM label_string_granularity_1\n" +
                "        WHERE dayno = '2020-11-25' AND (labelname = 'age_group') AND (labelvalue = '1-10')\n" +
                "    ) AS bitmap2\n" +
                "SELECT bitmapToArray(bitmapAnd(bitmap1,bitmap2)) as users";
        return sql;
    }

    public String getSqlUserGroupCount() {
        String sql = "WITH \n" +
                "    (\n" +
                "        SELECT uv AS user1\n" +
                "        FROM label_string_granularity_1\n" +
                "        WHERE dayno = '2020-11-25' AND (labelname = 'gender') AND (labelvalue = 'M')\n" +
                "    ) AS bitmap1, \n" +
                "    (\n" +
                "        SELECT uv AS user2\n" +
                "        FROM label_string_granularity_1\n" +
                "        WHERE dayno = '2020-11-25' AND (labelname = 'age_group') AND (labelvalue = '1-10')\n" +
                "    ) AS bitmap2\n" +
                "SELECT bitmapAndCardinality(bitmap1, bitmap2) AS count";
        return sql;
    }

    public String getSqlUserInsights() {
        String sql = "WITH \n" +
                "    (\n" +
                "        SELECT uv AS user1\n" +
                "                FROM label_string_granularity_1\n" +
                "                WHERE dayno = '2020-11-25' AND (labelname = 'gender') AND (labelvalue = 'M')\n" +
                "    ) AS checkoutUsers\n" +
                "SELECT \n" +
                "    labelname, \n" +
                "    labelvalue, \n" +
                "    bitmapAndCardinality(uv, checkoutUsers) AS count\n" +
                "FROM label_string_granularity_1\n" +
                "WHERE dayno = '2020-11-25' AND labelname = 'age_group'\n" +
                "ORDER BY count DESC\n" +
                "LIMIT 5";
        return sql;
    }

}
