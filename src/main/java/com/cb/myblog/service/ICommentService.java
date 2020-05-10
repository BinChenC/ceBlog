package com.cb.myblog.service;

import com.cb.myblog.domain.Blog;
import com.cb.myblog.domain.Comment;

/**
 * @author cb
 * @version 1.0
 * @date 2020/4/1 13:59
 * @Description 评论功能业务层
 */
public interface ICommentService {

    /**
     * 保存评论
     * @param comment
     */
    void save(Comment comment);

    /**
     * 根据 Id 获取父评论
     * @return
     */
    Comment getComment(Long id);

    void delete(Blog blog);
}
