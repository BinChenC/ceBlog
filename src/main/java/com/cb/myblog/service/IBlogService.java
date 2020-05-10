package com.cb.myblog.service;

import com.cb.myblog.domain.Blog;
import com.cb.myblog.domain.Tag;
import com.cb.myblog.domain.Type;
import com.cb.myblog.vo.IBlogRecommendVo;
import com.cb.myblog.vo.IBlogSimpleVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

/**
 * @author cb
 * @version 1.0
 * @date 2020/3/18 21:05
 */
public interface IBlogService {

    void updateViews(Long id);

    void updateCommentNum(Long id);

    boolean deleteBlog(Long id);

    boolean updateBlog(Long id, Blog blog);

    Long countBlogNum();

    Blog getBlog(Long id);

    Blog saveBlog(Blog blog,Boolean flag);

    List<IBlogRecommendVo> listRecommendBlogTop(Integer size);

    Map<String,List<Blog>> archiveBlog();

    Page<Blog> aPageBlogByTime(PageRequest pageRequest,String year);

    Page<IBlogSimpleVo> iPageBlogTitle(PageRequest pageRequest, String search);

    Page<IBlogSimpleVo> iPageBlog(PageRequest pageRequest);

    Page<Blog> listBlog(PageRequest pageRequest, String search);

    Page<Blog> listBlog(PageRequest pageRequest);

    Page<IBlogSimpleVo> tPageBlogByType(PageRequest pageRequest, Type type);

    Page<Blog> pageBolgTag(PageRequest pageRequest, Tag tag);

}