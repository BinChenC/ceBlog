package com.cb.myblog.vo;

import com.cb.myblog.domain.Type;

import java.util.Date;
import java.util.List;

/**
 * @author cb
 * @version 1.0
 * @date 2020/3/22 19:46
 */
public class BlogPage {

    private Long id;
    private String title;

    private Date updateTime;
    private Type type;

    public BlogPage() {
    }

    @Override
    public String toString() {
        return "BlogPage{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", updateTime=" + updateTime +
                ", type=" + type +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
