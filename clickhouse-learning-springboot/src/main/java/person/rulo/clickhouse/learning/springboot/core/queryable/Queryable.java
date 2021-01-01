package person.rulo.clickhouse.learning.springboot.core.queryable;

/**
 * @Author rulo
 * @Date 2020/11/23 15:51
 *
 * 查询接口类
 */
public interface Queryable<T, T1> {
    /**
     * 查询
     * @param t1
     * @return
     */
    T query(T1 t1);
}
