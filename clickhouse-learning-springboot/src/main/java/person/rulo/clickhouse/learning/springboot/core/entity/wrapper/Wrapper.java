package person.rulo.clickhouse.learning.springboot.core.entity.wrapper;

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

    /**
     * 验证箱内元素类型
     * @param className
     * @return
     */
    boolean isWrapperFor(String className);

}
