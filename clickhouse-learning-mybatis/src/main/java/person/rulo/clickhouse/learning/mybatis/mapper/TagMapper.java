package person.rulo.clickhouse.learning.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import person.rulo.clickhouse.learning.mybatis.entity.Tag;

import java.util.List;

/**
 * @Author rulo
 * @Date 2020/11/21 20:31
 */
@Mapper
public interface TagMapper {
    List<Tag> selectList();
}
