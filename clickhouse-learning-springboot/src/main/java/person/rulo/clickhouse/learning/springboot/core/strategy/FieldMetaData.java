package person.rulo.clickhouse.learning.springboot.core.strategy;

/**
 * @Author rulo
 * @Date 2020/11/25 20:59
 *
 * 字段元数据信息
 */
public class FieldMetaData {

    // 字段名称
    private String fieldName;
    // 字段别名
    private String fieldAlias;
    // 字段函数名
    private String fieldFunction;
    // 字段数据库类型名
    private String fieldType;
    // 字段 java 类型名
    private String fieldJavaType;
    // 是否主键
    private boolean isPrimary;
    // 函数是否为聚合函数
    private boolean isAggregateFunction;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldAlias() {
        return fieldAlias;
    }

    public void setFieldAlias(String fieldAlias) {
        this.fieldAlias = fieldAlias;
    }

    public String getFieldFunction() {
        return fieldFunction;
    }

    public void setFieldFunction(String fieldFunction) {
        this.fieldFunction = fieldFunction;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldJavaType() {
        return fieldJavaType;
    }

    public void setFieldJavaType(String fieldJavaType) {
        this.fieldJavaType = fieldJavaType;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }

    public boolean isAggregateFunction() {
        return isAggregateFunction;
    }

    public void setAggregateFunction(boolean aggregateFunction) {
        isAggregateFunction = aggregateFunction;
    }
}
