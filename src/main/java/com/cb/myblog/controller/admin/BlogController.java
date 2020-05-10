
package com.cb.myblog.controller.admin;

import com.cb.myblog.domain.Blog;
import com.cb.myblog.service.ICommentService;
import com.cb.myblog.vo.BlogInput;
import com.cb.myblog.domain.Tag;
import com.cb.myblog.domain.Type;
import com.cb.myblog.service.IBlogService;
import com.cb.myblog.service.ITagService;
import com.cb.myblog.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author cb
 * @version 1.0
 * @date 2020/3/18 22:04
 */
@RestController
@RequestMapping("/admin")
public class BlogController {
    @Autowired
    private IBlogService blogService;

    @Autowired
    private ITypeService typeService;

    @Autowired
    private ITagService tagService;

    @Autowired
    private ICommentService commentService;

    private SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");

    /**
     * 分页查询博客 + 条件查询
     */
    @CrossOrigin
    @GetMapping("/blogs/{pageNum}/{pageSize}/{condition}")
    public Page<Blog> blogs(@PathVariable("pageNum") Integer pageNum,
                            @PathVariable("pageSize") Integer pageSize,
                            @PathVariable("condition") String search) {

        PageRequest p = PageRequest.of(pageNum, pageSize);
        Page<Blog> blogs = blogService.listBlog(p, search);

        return blogs;
    }

    /**
     * 分页查询博客
     */
    @CrossOrigin
    @GetMapping("/blogs/{pageNum}/{pageSize}")
    public Page<Blog> blogsAll(@PathVariable("pageNum") Integer pageNum,
                               @PathVariable("pageSize") Integer pageSize) {

        PageRequest p = PageRequest.of(pageNum, pageSize);
        Page<Blog> blogs = blogService.listBlog(p);

        return blogs;
    }

    /**
     * XXX: 新增博客操作...user认证暂时无法解决
     */
    @CrossOrigin
    @PostMapping("/blogs")
    public String input(@RequestBody BlogInput blogInput) {
//        System.out.println(blogInput);
        Type type = typeService.getType(blogInput.getSort());
        List<Tag> tags = tagService.listTag(blogInput.getTags());
        Blog blog = new Blog();
        blog.setFlag(blogInput.getFlag());
        blog.setDescription(blogInput.getDescription());
        blog.setTitle(blogInput.getTitle());
        blog.setContent(blogInput.getContent());
        if ("".equals(blogInput.getImageUrl()) || blogInput.getImageUrl() == null) {
            blog.setFirstPicture("https://unsplash.it/800/450?image=1079");
        } else {
            blog.setFirstPicture(blogInput.getImageUrl());
        }
        blog.setType(type);
        blog.setTags(tags);
        blogService.saveBlog(blog, true);

        return "success";
    }

    /**
     * 图片上传功能。
     */
    @CrossOrigin
    @PostMapping("/import")
    public String importData(MultipartFile file, HttpServletRequest req) throws IOException {
        String format = sdf.format(new Date());
        String realPath = req.getServletContext().getRealPath("/upload") + format;
        File folder = new File(realPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String oldName = file.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."));
        file.transferTo(new File(folder, newName));
        String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + "/blog/upload" + format + newName;
        System.out.println(url);
        return url;
    }

    /**
     * XXX: 更新博客操作...user认证暂时无法解决.
     */
    @CrossOrigin
    @PutMapping("/blogs/{id}")
    public String update(@PathVariable("id") Long id,
                         @RequestBody BlogInput blogInput) {
        Blog result = new Blog();
        Blog prototype = blogService.getBlog(id);
        result.setId(id);

        Type type = typeService.getType(blogInput.getSort());
        List<Tag> tags = tagService.listTag(blogInput.getTags());
        result.setType(type);
        result.setTags(tags);

        result.setCommentNum(prototype.getCommentNum());
        result.setComments(prototype.getComments());
        result.setViews(prototype.getViews());
        result.setLikes(prototype.getLikes());

        result.setFlag(blogInput.getFlag());
        result.setDescription(blogInput.getDescription());
        result.setTitle(blogInput.getTitle());
        result.setContent(blogInput.getContent());
        result.setCreateTime(blogInput.getCreateTime());

        if ("".equals(blogInput.getImageUrl()) || blogInput.getImageUrl() == null) {
            result.setFirstPicture("https://unsplash.it/800/450?image=1079");
        } else {
            result.setFirstPicture(blogInput.getImageUrl());
        }

        blogService.saveBlog(result, false);

        return "success";
    }

    /**
     * 删除博客
     */
    @CrossOrigin
    @DeleteMapping("/blogs/{id}")
    public String deleteType(@PathVariable String id) {
        Blog blog = blogService.getBlog(Long.valueOf(id));
        if (blog != null) {
            commentService.delete(blog);
            blogService.deleteBlog(Long.valueOf(id));
            return "success";
        }

        return "notExists";
    }

}