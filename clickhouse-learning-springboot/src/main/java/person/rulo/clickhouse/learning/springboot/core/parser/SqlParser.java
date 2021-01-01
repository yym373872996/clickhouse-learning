package person.rulo.clickhouse.learning.springboot.core.parser;

/**
 * @Author rulo
 * @Date 2020/11/26 15:56
 *
 * SQL 解析器
 */
public interface SqlParser<T> {
    T parseSql(String sql);
}
