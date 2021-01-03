package person.rulo.clickhouse.learning.springboot.core.entity.wrapper;

/**
 * @Author rulo
 * @Date 2021/1/2 16:28
 *
 * 抽象包装器
 */
public abstract class AbstractWrapper<T> implements Wrapper<T> {

    private T content;

    public AbstractWrapper(T content) {
        this.content = content;
    }

    @Override
    public T getContent() {
        return content;
    }

    @Override
    public void setContent(T content) {
        this.content = content;
    }

    @Override
    public boolean isWrapperFor(String className) {
        return content.getClass().getName().equals(className);
    }
}
