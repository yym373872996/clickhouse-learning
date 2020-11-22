package person.rulo.clickhouse.learning.mybatis.service.impl;

import org.springframework.stereotype.Service;
import person.rulo.clickhouse.learning.mybatis.entity.Tag;
import person.rulo.clickhouse.learning.mybatis.mapper.TagMapper;
import person.rulo.clickhouse.learning.mybatis.service.TagService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author rulo
 * @Date 2020/11/21 20:59
 */
@Service
public class TagServiceImpl implements TagService {

    @Resource
    private TagMapper tagMapper;

    public List<Tag> selectList() {
        return tagMapper.selectList();
    }
}
