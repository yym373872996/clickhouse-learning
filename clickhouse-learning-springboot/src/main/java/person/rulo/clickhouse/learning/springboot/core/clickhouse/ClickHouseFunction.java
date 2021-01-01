package person.rulo.clickhouse.learning.springboot.core.clickhouse;

/**
 * @Author rulo
 * @Date 2020/11/26 21:03
 *
 * ClickHouse 函数类型
 */
public enum ClickHouseFunction {

    UNKNOWN("unknown", false),
    COUNT("count", true),
    SUM("sum", true),
    MIN("min", true),
    MAX("max", true),
    AVG("avg", true),
    BITMAP_TO_ARRAY("bitmapToArray", false),
    BITMAP_CONTAINS("bitmapContains", false),
    BITMAP_CARDINALITY("bitmapCardinality", true),
    BITMAP_AND("bitmapAnd", false),
    BITMAP_OR("bitmapOr", false),
    BITMAP_XOR("bitmapXor", false),
    BITMAP_ANDNOT("bitmapAndnot", false),
    BITMAP_AND_CARDINALITY("bitmapAndCardinality", true),
    BITMAP_OR_CARDINALITY("bitmapOrCardinality", true),
    BITMAP_XOR_CARDINALITY("bitmapXorCardinality", true),
    BITMAP_ANDNOT_CARDINALITY("bitmapAndnotCardinality", true);
    
    private String name;
    private boolean isAggregateFunction;

    ClickHouseFunction(String name, boolean isAggregateFunction) {
        this.name = name;
        this.isAggregateFunction = isAggregateFunction;
    }

    public static ClickHouseFunction getValue(String name) {
        for (ClickHouseFunction function : values()) {
            if (name.equals(function.getName())) {
                return function;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public boolean isAggregateFunction() {
        return isAggregateFunction;
    }
}
