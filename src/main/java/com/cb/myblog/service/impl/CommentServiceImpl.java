package com.cb.myblog.service.impl;

import com.cb.myblog.domain.Blog;
import com.cb.myblog.domain.Comment;
import com.cb.myblog.repository.ICommentDao;
import com.cb.myblog.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author cb
 * @version 1.0
 * @date 2020/4/1 14:01
 * @Description 评论功能业务层实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CommentServiceImpl implements ICommentService {

    @Autowired
    ICommentDao commentDao;

    @Override
    public void delete(Blog blog){
        commentDao.deleteByBlog(blog);
    }

    @Override
    public void save(Comment comment) {
        commentDao.save(comment);
    }

    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    @Override
    public Comment getComment(Long id) {
        return commentDao.getOne(id);
    }
}
