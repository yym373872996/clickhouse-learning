package person.rulo.clickhouse.learning.springboot.core.clickhouse;

/**
 * @Author rulo
 * @Date 2020/11/30 19:10
 *
 * ClickHouse 字段类型与 jdbc 返回的 java 类型映射表
 */
public enum ClickHouseTypeMapper {

    INT8("Int8", "java.lang.Short"),
    INT16("Int16", "java.lang.Short"),
    INT32("Int32", "java.lang.Integer"),
    INT64("Int64", "java.lang.Long"),
    UINT8("UInt8", "java.lang.Short"),
    UINT16("UInt16", "java.lang.Integer"),
    UINT32("UInt32", "java.lang.Long"),
    UINT64("UInt64", "java.lang.BigInteger"),
    FLOAT32("Float32", "java.lang.Float"),
    FLOAT64("Float64", "java.lang.Double"),
    STRING("String", "java.lang.String"),
    ARRAY("Array", "java.sql.Array");

    private String fieldType;
    private String javaType;

    ClickHouseTypeMapper(String fieldType, String javaType) {
        this.fieldType = fieldType;
        this.javaType = javaType;
    }

    public static ClickHouseTypeMapper getValue(String fieldType) {
        for (ClickHouseTypeMapper mapper : values()) {
            if (fieldType.equals(mapper.getFieldType())) {
                return mapper;
            }
        }
        return null;
    }

    public String getFieldType() {
        return fieldType;
    }

    public String getJavaType() {
        return javaType;
    }

}
