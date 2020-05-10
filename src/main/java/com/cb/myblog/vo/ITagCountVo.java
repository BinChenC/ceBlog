package com.cb.myblog.vo;

import com.cb.myblog.domain.Blog;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cb
 * @version 1.0
 * @date 2020/3/28 19:45
 * @Description
 */
@ToString
public class ITagCountVo {

    private Long id;
    private String name;
    private List<Blog> blogs = new ArrayList<>();

    public ITagCountVo() {
        super();
    }

    public ITagCountVo(Long id, String name, List<Blog> blogs) {
        super();
        this.id = id;
        this.name = name;

        this.blogs = blogs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }
}
