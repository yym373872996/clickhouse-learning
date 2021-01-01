package person.rulo.clickhouse.learning.springboot.core.data.wrapper;

/**
 * @Author rulo
 * @Date 2020/11/24 19:40
 *
 * 包装器接口
 */
public interface Wrapper<T> {

    /**
     * 拆箱
     * @return
     */
    T getContent();

    /**
     * 装箱
     * @param content
     */
    void setContent(T content);

}
