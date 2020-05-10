package com.cb.myblog.controller.user;

import com.cb.myblog.domain.Blog;
import com.cb.myblog.domain.Tag;
import com.cb.myblog.domain.Type;
import com.cb.myblog.service.IBlogService;
import com.cb.myblog.service.ITagService;
import com.cb.myblog.service.ITypeService;
import com.cb.myblog.vo.IBlogSimpleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author cb
 * @version 1.0
 * @date 2020/4/3 16:01:02
 * @Description 标签详情页
 */
@RestController
@RequestMapping("/user")
public class TagDetailsController {

    @Autowired
    private ITagService tagService;

    @Autowired
    private IBlogService blogService;

    /**
     * 查询所有标签
     */
    @CrossOrigin
    @GetMapping("/tagAll")
    public List<Tag> tagAll() {

        return tagService.findAllTags();
    }

    /**
     * 根据分类，进行分页查询博客
     */
    @CrossOrigin
    @GetMapping("/tagDetails/{id}/{pageNum}/{pageSize}")
    public Page<Blog> findBlogByTypeId(@PathVariable("id") Long tagId,
                                       @PathVariable("pageNum") Integer pageNum,
                                       @PathVariable("pageSize") Integer pageSize) {


        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        PageRequest p = PageRequest.of(pageNum, pageSize,sort);
        Tag tag = tagService.getTag(tagId);
        Page<Blog> blogs = blogService.pageBolgTag(p, tag);

        return blogs;
    }

}
