package person.rulo.clickhouse.learning.springboot.core.parser;

import org.springframework.stereotype.Component;
import person.rulo.clickhouse.learning.springboot.common.CommonRegex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author rulo
 * @Date 2020/11/26 19:30
 *
 * SQL 解析帮助类
 */
@Component
public class SqlParseHelper {

    /**
     * 获取字段的函数名
     * @param fieldName
     * @return
     */
    public String getFieldFunction(String fieldName) {
        Pattern pattern = Pattern.compile(CommonRegex.CONTAIN_PARENTHESES);
        Matcher matcher = pattern.matcher(fieldName);
        while (matcher.find()) {
            return fieldName.replaceAll(CommonRegex.CONTAIN_PARENTHESES, "");
        }
        return null;
    }
}
