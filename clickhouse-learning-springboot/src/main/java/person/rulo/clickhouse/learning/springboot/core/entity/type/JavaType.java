package person.rulo.clickhouse.learning.springboot.core.entity.type;

/**
 * @Author rulo
 * @Date 2020/11/30 19:10
 *
 * Java 类型
 */
public enum JavaType {

    SHORT("java.lang.Short"),
    INTEGER("java.lang.Integer"),
    LONG("java.lang.Long"),
    BIGINTEGER("java.lang.BigInteger"),
    FLOAT("java.lang.Float"),
    DOUBLE("java.lang.Double"),
    STRING("java.lang.String");

    private String name;

    JavaType(String name) {
        this.name = name;
    }

    public static JavaType getValue(String name) {
        for (JavaType javaType : values()) {
            if (javaType.getName() == name) {
                return javaType;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }
}
