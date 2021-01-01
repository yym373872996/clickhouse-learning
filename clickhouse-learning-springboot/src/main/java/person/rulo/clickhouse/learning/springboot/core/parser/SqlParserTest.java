package person.rulo.clickhouse.learning.springboot.core.parser;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.ast.statement.SQLWithSubqueryClause;
import com.alibaba.druid.util.JdbcConstants;

import java.util.List;

/**
 * @Author rulo
 * @Date 2020/11/26 14:12
 */
public class SqlParserTest {

    public static void main(String[] args) {
        DbType dbType = JdbcConstants.CLICKHOUSE;
        SQLStatement statement = SQLUtils.parseSingleStatement(getSqlUserGroup(), dbType, false);

        SQLSelectStatement selectStatement = (SQLSelectStatement)statement;
//        System.out.println(selectStatement + "\n");
        SQLSelect sqlSelect = selectStatement.getSelect();
//        System.out.println(sqlSelect + "\n");
        sqlSelect.getQueryBlock().getSelectList().forEach(selectItem -> {
            System.out.println(selectItem.getExpr().toString() + "\n");
        });
        SQLWithSubqueryClause withSubqueryClause = sqlSelect.getWithSubQuery();
//        System.out.println(withSubqueryClause + "\n");
        List<SQLWithSubqueryClause.Entry> entries = withSubqueryClause.getEntries();
        entries.forEach(entry -> {
            SQLSelect sqlSelect1 = entry.getSubQuery();
//            System.out.println(sqlSelect1 + "\n");
        });
//        SQLSelectQueryBlock sqlSelectQueryBlock = sqlSelect.getFirstQueryBlock();
        SQLSelectQueryBlock sqlSelectQueryBlock = sqlSelect.getQueryBlock();
//        System.out.println(sqlSelectQueryBlock + "\n");
        sqlSelectQueryBlock.getSelectList().forEach(System.out::println);
    }

    public static String getSqlUserProfiles() {
        String sql = "SELECT \n" +
                "    labelname, \n" +
                "    labelvalue\n" +
                "FROM label_string_granularity_1\n" +
                "WHERE dayno = '2020-11-25' AND bitmapContains(uv, toUInt32(5)) = 1\n" +
                "LIMIT 3\n" +
                "settings merge_tree_min_rows_for_concurrent_read=100";
        return sql;
    }

    public static String getSqlUserGroup() {
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

    public static String getSqlUserGroupCount() {
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

    public static String getSqlUserInsights() {
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
