package com.cb.myblog.controller.user;

import com.cb.myblog.domain.Blog;
import com.cb.myblog.service.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author cb
 * @version 1.0
 * @date 2020/4/3 22:19
 * @Description 归档详情页
 */
@RestController
@RequestMapping("/user")
public class ArchiveShowController {

    @Autowired
    IBlogService blogService;

    @CrossOrigin
    @GetMapping("/archives")
    public Map<String, List<Blog>> archives(){

        return blogService.archiveBlog();
    }

    @CrossOrigin
    @GetMapping("/archives/{year}/{pageNum}/{pageSize}")
    public Page<Blog> archives(@PathVariable("year") String year,
                               @PathVariable("pageNum") Integer pageNum,
                               @PathVariable("pageSize") Integer pageSize){
        PageRequest pageRequest = PageRequest.of(pageNum, pageSize);

        return blogService.aPageBlogByTime(pageRequest,year);
    }

    @CrossOrigin
    @GetMapping("/archives/count")
    public Long count(){

        return blogService.countBlogNum();
    }



}
