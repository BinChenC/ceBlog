package com.cb.myblog.vo;

/**
 * @author cb
 * @version 1.0
 * @date 2020/4/1 13:18
 * @Description 接收博客详情页提交的评论数据
 */
public class BlogDetailsVo {

    private Long blogId;
    private Long cmtParentId;
    private String content;
    private String nickname;
    private String email;

    public BlogDetailsVo() {
    }

    public BlogDetailsVo(Long blogId, Long cmtParentId, String content, String nickname, String email) {
        this.blogId = blogId;
        this.cmtParentId = cmtParentId;
        this.content = content;
        this.nickname = nickname;
        this.email = email;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public Long getCmtParentId() {
        return cmtParentId;
    }

    public void setCmtParentId(Long cmtParentId) {
        this.cmtParentId = cmtParentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "BlogDetailsVo{" +
                "blogId=" + blogId +
                ", cmtParentId=" + cmtParentId +
                ", content='" + content + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
