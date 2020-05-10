package com.cb.myblog.controller.user;

import com.cb.myblog.domain.Blog;
import com.cb.myblog.domain.Type;
import com.cb.myblog.service.IBlogService;
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
 * @date 2020/4/2 16:48
 * @Description 分类详情页
 */
@RestController
@RequestMapping("/user")
public class TypeDetailsController {

    @Autowired
    private ITypeService typeService;

    @Autowired
    private IBlogService blogService;

    /**
     * 查询所有分类
     */
    @CrossOrigin
    @GetMapping("/classify")
    public List<Type> typeAll() {
        return typeService.findAllTypes();
    }

    /**
     * 根据分类，进行分页查询博客
     */
    @CrossOrigin
    @GetMapping("/classifyBlog/{id}/{pageNum}/{pageSize}")
    public Page<IBlogSimpleVo> findBlogByTypeId(@PathVariable("id") Long typeId,
                                       @PathVariable("pageNum") Integer pageNum,
                                       @PathVariable("pageSize") Integer pageSize) {

        Type type = typeService.getType(typeId);
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        PageRequest p = PageRequest.of(pageNum, pageSize,sort);
        Page<IBlogSimpleVo> tBlogs = blogService.tPageBlogByType(p, type);

        return tBlogs;
    }

}
