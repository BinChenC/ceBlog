package com.cb.myblog.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 文章的关联信息
 */
@Entity
@Table(name = "t_blog")
@ToString
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"},ignoreUnknown = true)
public class Blog implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//主键id
    private String title;//文章标题

    @Basic(fetch = FetchType.EAGER)
//    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String description;//博客描述

    @Basic(fetch = FetchType.EAGER)
//    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String content; //文章内容
    private String firstPicture;    //首图地址
    private String flag;    //原创或转载
    private Integer views = 0;  //观看次数
    private Integer commentNum = 0; //评论数
    private Integer likes = 0;  //点赞次数
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;    //创建时间
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;    //更新时间

    @ManyToOne
    @ToString.Exclude
    @JsonIgnoreProperties(value = {"blogs"})
    private Type type;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @ToString.Exclude
    @JsonIgnoreProperties(value = {"blogs"})
    private List<Tag> tags = new ArrayList<>();

    @ManyToOne
    @ToString.Exclude
    @JsonIgnoreProperties(value = {"password","username"})
    private User user;

    @OneToMany(mappedBy = "blog")
    @ToString.Exclude
    @JsonIgnoreProperties(value = {"blog"})
    private List<Comment> comments = new ArrayList<>();

    public Blog() {
        super();
    }

    public Blog(String title, String description, String content, String firstPicture, String flag, Integer views, Integer commentNum, Integer likes, Date createTime, Date updateTime, Type type, List<Tag> tags, User user, List<Comment> comments) {
        super();
        this.title = title;
        this.description = description;
        this.content = content;
        this.firstPicture = firstPicture;
        this.flag = flag;
        this.views = views;
        this.commentNum = commentNum;
        this.likes = likes;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.type = type;
        this.tags = tags;
//        User substitute = new User();
//        BeanUtils.copyProperties(user,substitute);
//        substitute.setUsername("");
//        substitute.setPassword("");
        this.user = user;
        this.comments = comments;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
