package person.rulo.clickhouse.learning.springboot.core.data.type;

/**
 * @Author rulo
 * @Date 2020/11/25 21:11
 *
 * 合并操作类型
 */
public enum MergingOperation {
    // 堆叠
    UNION,
    // 计总数
    COUNT,
    // 求和
    SUM,
    // 求最小值
    MIN,
    // 求最大值
    MAX,
    // 求平均值
    AVG,
    // 求且
    AND
}
