package person.rulo.clickhouse.learning.springboot.core.entity.wrapper.datasouce;

import person.rulo.clickhouse.learning.springboot.core.entity.wrapper.AbstractWrapper;
import person.rulo.clickhouse.learning.springboot.core.entity.wrapper.Wrapper;

/**
 * @Author rulo
 * @Date 2020/11/23 16:28
 *
 * 数据源包装器
 */
public abstract class DataSourceWrapper<T> extends AbstractWrapper<T> {

    public DataSourceWrapper(T content) {
        super(content);
    }
}
