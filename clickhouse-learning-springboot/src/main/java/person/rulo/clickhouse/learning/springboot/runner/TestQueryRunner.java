package person.rulo.clickhouse.learning.springboot.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import person.rulo.clickhouse.learning.springboot.common.ResponseCode;
import person.rulo.clickhouse.learning.springboot.core.clickhouse.ClickHouseClusterQuerier;
import person.rulo.clickhouse.learning.springboot.core.entity.request.SqlQueryRequest;
import person.rulo.clickhouse.learning.springboot.core.entity.response.QueryResponse;
import person.rulo.clickhouse.learning.springboot.core.entity.wrapper.datasouce.ListDataSourceWrapper;
import person.rulo.clickhouse.learning.springboot.core.entity.wrapper.result.ResultWrapper;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.sql.RowSet;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author rulo
 * @Date 2020/11/24 19:24
 */
@Component
public class TestQueryRunner implements ApplicationRunner {

    private static Logger logger = LoggerFactory.getLogger(TestQueryRunner.class);

    @Resource
    List<DataSource> dataSourceList;
    @Resource
    ClickHouseClusterQuerier querier;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Map<String, String> sqls = new HashMap<>();
        sqls.put("queryUserGroup", getSqlUserGroup());
        sqls.put("queryUserGroupCount", getSqlUserGroupCount());
        sqls.put("queryUserProfiles", getSqlUserProfiles());
        sqls.put("queryUserInsights", getSqlUserInsights());
        sqls.forEach((name, sql) -> {
            ListDataSourceWrapper dataSourceWrapper = new ListDataSourceWrapper(dataSourceList);
            SqlQueryRequest request = new SqlQueryRequest();
            request.setDataSourceWrapper(dataSourceWrapper);
            request.setSql(sql);
            logger.info("{} start", name);
            QueryResponse response = querier.query(request);
            String code = response.getCode();
            logger.info("execution end, code: {}, time cost: {} ms", code, response.getCostTime());
            if (ResponseCode.SUCCESS.getCode().equals(code)) {
                ResultWrapper resultWrapper = response.getResultWrapper();
                Object content = resultWrapper.getContent();
                if (content instanceof List) {
                    List list = (List) content;
                    logger.info("union result: {}", list);
                    list.forEach(item -> {
                        if (item instanceof Map) {
                            ((Map) item).forEach((columnName, columnValue) -> {
                                String value = null;
                                if (columnValue.getClass().isArray()) {
                                    value = Arrays.toString((Object[]) columnValue);
                                }
                                if (columnValue instanceof String) {
                                    value = columnValue.toString();
                                }
                                logger.info("each result: { {}: {} }", columnName, value);
                            });
                        }
                    });
                } else if (content instanceof Map) {
                    logger.info("aggregate result: {}", content);
                }
            }
            logger.info("{} end", name);
        });
    }

    public String getSqlUserProfiles() {
        String sql = "SELECT \n" +
                "    labelname, \n" +
                "    labelvalue\n" +
                "FROM label_string_granularity_1\n" +
                "WHERE dayno = '2020-11-25' AND bitmapContains(uv, toUInt32(10)) = 1\n" +
                "LIMIT 3";
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
