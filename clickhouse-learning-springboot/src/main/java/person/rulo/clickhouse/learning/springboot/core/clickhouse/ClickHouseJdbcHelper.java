package person.rulo.clickhouse.learning.springboot.core.clickhouse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import person.rulo.clickhouse.learning.springboot.common.CommonRegex;

import java.sql.Array;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author rulo
 * @Date 2020/11/27 18:49
 *
 * ClickHouse JDBC 帮助类
 */
@Component
public class ClickHouseJdbcHelper {

    private static Logger logger = LoggerFactory.getLogger(ClickHouseJdbcHelper.class);

    /**
     * 根据 ClickHouse 字段类型返回 java 字段值
     * @param fieldValue
     * @param fieldType
     * @return
     */
    public Object getValueByFieldType(Object fieldValue, String fieldType) throws SQLException {
        /* 判断是否为复合类型 */
        Pattern pattern = Pattern.compile(CommonRegex.CONTAIN_PARENTHESES);
        Matcher matcher = pattern.matcher(fieldType);
        while (matcher.find()) {
            // 如果是复合类型，截取主类型名称
            fieldType = fieldType.replaceAll(CommonRegex.CONTAIN_PARENTHESES, "");
        }
        ClickHouseTypeMapper mapper = ClickHouseTypeMapper.getValue(fieldType);
        if (mapper != null) {
            switch (mapper) {
                case ARRAY:
                    // 如果是 Array 类型则获取具体的值
                    return ((Array) fieldValue).getArray();
            }
        }
        return fieldValue;
    }

    /**
     * 根据 ClickHouse 字段类型返回 java 类型
     * @param fieldType
     * @return
     */
    public String fieldTypeToJavaType(String fieldType) {
        ClickHouseTypeMapper mapper = ClickHouseTypeMapper.getValue(fieldType);
        if (mapper != null) {
            return mapper.getJavaType();
        }
        return null;
    }
}
