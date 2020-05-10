package com.cb.myblog.controller.user;

import com.cb.myblog.domain.Blog;
import com.cb.myblog.domain.Tag;
import com.cb.myblog.domain.Type;
import com.cb.myblog.service.IBlogService;
import com.cb.myblog.service.ITagService;
import com.cb.myblog.service.ITypeService;
import com.cb.myblog.vo.IBlogRecommendVo;
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
 * @date 2020/3/27 18:42
 */
@RestController
@RequestMapping("/user")
public class IndexController {

    @Autowired
    private IBlogService blogService;

    @Autowired
    private ITypeService typeService;

    @Autowired
    private ITagService tagService;

    @GetMapping("/blogs/{pageNum}/{pageSize}/{condition}")
    @CrossOrigin
    public Page<IBlogSimpleVo> iBlogs(@PathVariable("pageNum") Integer pageNum,
                         @PathVariable("pageSize") Integer pageSize,
                         @PathVariable("condition") String search){

        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");

        PageRequest p = PageRequest.of(pageNum, pageSize,sort);
        Page<IBlogSimpleVo> blogs = blogService.iPageBlogTitle(p,search);

        return blogs;
    }

    /**
     * 分页查询博客
     */
    @CrossOrigin
    @GetMapping("/blogs/{pageNum}/{pageSize}")
    public Page<IBlogSimpleVo> blogsAll(@PathVariable("pageNum") Integer pageNum,
                                        @PathVariable("pageSize") Integer pageSize) {

        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");

        PageRequest p = PageRequest.of(pageNum, pageSize, sort);
        Page<IBlogSimpleVo> blogs = blogService.iPageBlog(p);

        return blogs;
    }

    /**
     * 博客详情页，根据 id 查询博客信息
     */
    @CrossOrigin
    @GetMapping("/blogs/{id}")
    public Blog findOne(@PathVariable("id") Long id) {

        blogService.updateViews(id);

        return blogService.getBlog(id);
    }

    @GetMapping("/types")
    @CrossOrigin
    public List<Type> iTypes(){

        return typeService.listTypeTop(6);
    }

    @GetMapping("/tags")
    @CrossOrigin
    public List<Tag> iTags(){

        return tagService.listTagTop(10);
    }

    @GetMapping("/recommendBlog")
    @CrossOrigin
    public List<IBlogRecommendVo> iRecommendBlog(){

        return blogService.listRecommendBlogTop(5);
    }

}
