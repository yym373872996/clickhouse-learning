package person.rulo.clickhouse.learning.springboot.core.clickhouse;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.util.JdbcConstants;
import org.springframework.stereotype.Component;
import person.rulo.clickhouse.learning.springboot.core.parser.SqlParser;

/**
 * @Author rulo
 * @Date 2020/11/26 16:50
 *
 * ClickHouse SQL 解析器，采用 Druid 中的解析器
 */
@Component
public class ClickHouseSqlParser implements SqlParser<SQLStatement> {
    @Override
    public SQLStatement parseSql(String sql) {
        DbType dbType = JdbcConstants.CLICKHOUSE;
        return SQLUtils.parseSingleStatement(sql, dbType, false);
    }
}
