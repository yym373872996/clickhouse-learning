package person.rulo.clickhouse.learning.mybatis.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import person.rulo.clickhouse.learning.mybatis.entity.Tag;
import person.rulo.clickhouse.learning.mybatis.service.TagService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author rulo
 * @Date 2020/11/21 21:03
 */
@RestController
@RequestMapping("/tag")
public class TagController {

    @Resource
    private TagService tagService;

    @RequestMapping("/selectList")
    public List<Tag> selectList() {
        return tagService.selectList();
    }

}
