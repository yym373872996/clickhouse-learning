package person.rulo.clickhouse.learning.mybatis.service;

import person.rulo.clickhouse.learning.mybatis.entity.Tag;

import java.util.List;

/**
 * @Author rulo
 * @Date 2020/11/21 20:57
 */
public interface TagService {
    List<Tag> selectList();
}
