package person.rulo.clickhouse.learning.springboot.core.entity.wrapper.result;

import person.rulo.clickhouse.learning.springboot.core.entity.wrapper.AbstractWrapper;
import person.rulo.clickhouse.learning.springboot.core.entity.wrapper.Wrapper;

/**
 * @Author rulo
 * @Date 2020/11/24 10:22
 *
 * 结果集包装器
 */
public abstract class ResultWrapper<T> extends AbstractWrapper<T> {

    public ResultWrapper(T content) {
        super(content);
    }
}
