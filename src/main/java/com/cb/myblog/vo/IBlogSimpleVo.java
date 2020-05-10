package com.cb.myblog.vo;

import com.cb.myblog.domain.Comment;
import com.cb.myblog.domain.Type;
import com.cb.myblog.domain.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author cb
 * @version 1.0
 * @date 2020/3/28 0:14
 * @Description 用于首页博客映射
 */
@ToString
public class IBlogSimpleVo {

    private long id;
    private String title;
    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String description;
    private int views;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    private int commentNum;
    private int likes;
    private Type type;
    private String firstPicture;
    private User user;

    public IBlogSimpleVo() {
        super();
    }

    public IBlogSimpleVo(long id, String title, String description, int views, Date createTime, int commentNum, int likes, Type type, String firstPicture, User user) {
        super();
        this.id = id;
        this.title = title;
        this.description = description;
        this.views = views;
        this.createTime = createTime;
        this.commentNum = commentNum;
        this.likes = likes;
        this.type = type;
        this.firstPicture = firstPicture;
        User test = new User();
        BeanUtils.copyProperties(user,test);
        test.setUsername("");
        test.setPassword("");
        test.setCreateTime(null);
        test.setUpdateTime(null);
        this.user = test;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
