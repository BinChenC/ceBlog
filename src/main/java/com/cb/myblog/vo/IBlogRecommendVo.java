package com.cb.myblog.vo;

import lombok.ToString;

/**
 * @author cb
 * @version 1.0
 * @date 2020/3/27 23:49
 * @Description 用于首页最新推荐博客的映射
 */
@ToString
public class IBlogRecommendVo {

    private Long id;
    private String title;

    public IBlogRecommendVo() {
        super();
    }

    public IBlogRecommendVo(Long id, String title) {
        super();
        this.id = id;
        this.title = title;
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

}
