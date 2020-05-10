package com.cb.myblog.repository;

import com.cb.myblog.domain.Blog;
import com.cb.myblog.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author cb
 * @version 1.0
 * @date 2020/4/1 13:58
 * @Description 评论功能持久层
 */
public interface ICommentDao extends JpaRepository<Comment,Long> {

    void deleteByBlog(Blog blog);

}
