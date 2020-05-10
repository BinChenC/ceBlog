package com.cb.myblog.controller.user;

import com.cb.myblog.domain.Blog;
import com.cb.myblog.domain.Comment;
import com.cb.myblog.service.IBlogService;
import com.cb.myblog.service.ICommentService;
import com.cb.myblog.vo.BlogDetailsVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author cb
 * @version 1.0
 * @date 2020/4/1 13:04
 * @Description 博客详情页
 */
@RestController
@RequestMapping("/user")
public class BlogDetailsController {
    @Autowired
    IBlogService blogService;

    @Autowired
    ICommentService commentService;

    @CrossOrigin
    @PostMapping("/blog")
    public String saveComment(@RequestBody BlogDetailsVo blogDetailsVo){

        if (blogDetailsVo.getNickname().contains("zPVwibWlaSB0ZWF") || blogDetailsVo.getNickname().length() > 5) {
            return null;
        }

        String[] flag = blogDetailsVo.getContent().split("zPVwibWlaSB0ZWF");
        if (flag.length > 2) {
            return null;
        }
        Blog blog = blogService.getBlog(blogDetailsVo.getBlogId());
        if (blog == null) {
            return null;
        }
        Comment comment = new Comment();
        comment.setBlog(blog);
        comment.setNickname(blogDetailsVo.getNickname());
        comment.setContent(blogDetailsVo.getContent());
        comment.setAvatar("https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png");
        comment.setEmail(blogDetailsVo.getEmail());
        comment.setCreateTime(new Date());
        Comment parent = null;
        if (blogDetailsVo.getCmtParentId() != null && blogDetailsVo.getCmtParentId() > 0) {
             parent = commentService.getComment(blogDetailsVo.getCmtParentId());
        }
        comment.setParentComment(parent);
        commentService.save(comment);
        blogService.updateCommentNum(blogDetailsVo.getBlogId());
        return "success";
    }

}
