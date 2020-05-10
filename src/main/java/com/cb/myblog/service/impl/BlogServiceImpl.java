package com.cb.myblog.service.impl;

import com.cb.myblog.domain.Blog;
import com.cb.myblog.domain.Tag;
import com.cb.myblog.domain.Type;
import com.cb.myblog.domain.User;
import com.cb.myblog.repository.IBlogDao;
import com.cb.myblog.service.IBlogService;
import com.cb.myblog.vo.IBlogRecommendVo;
import com.cb.myblog.vo.IBlogSimpleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * @author cb
 * @version 1.0
 * @date 2020/3/18 21:09
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BlogServiceImpl implements IBlogService {

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Autowired
    private IBlogDao blogDao;

    @Override
    public void updateViews(Long id) {
        blogDao.updateViews(id);
    }

    @Override
    public void updateCommentNum(Long id) {
        blogDao.updateCommentNum(id);
    }

    @Override
    public boolean deleteBlog(Long id) {
        if (blogDao.existsById(id)) {
            blogDao.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateBlog(Long id, Blog blog) {
        if (!blogDao.existsById(id)) {
            return false;
        }
        blog.setUpdateTime(new Date());
        blogDao.save(blog);
        return true;
    }

    @Override
    public Long countBlogNum() {

        return blogDao.count();
    }

    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    @Override
    public Blog getBlog(Long id) {
        return blogDao.getOne(id);
    }

    @Override
    public Blog saveBlog(Blog blog,Boolean flag) {

        if (flag) {
            blog.setUser((User) redisTemplate.opsForValue().get("user"));
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setLikes(5 + (int) (Math.random() * (20 - 5 + 1)));
        } else {
            blog.setUser((User) redisTemplate.opsForValue().get("user"));
            blog.setUpdateTime(new Date());
        }
        return blogDao.save(blog);
    }

    /**
     * 推荐博客中，按照时间进行排序
     */
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    @Override
    public List<IBlogRecommendVo> listRecommendBlogTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"updateTime");
        PageRequest pageRequest = PageRequest.of(0, size, sort);

        return blogDao.findTop(pageRequest);
    }

    /**
     * 归档
     */
    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogDao.findGroupYear();
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : years) {
            map.put(year,blogDao.findByYear(year));
        }

        return map;
    }

    @Override
    public Page<Blog> aPageBlogByTime(PageRequest pageRequest, String year) {

        return blogDao.pageFindBlogsByYear(pageRequest,year);
    }

    /**
     * 博客首页的条件查询博客
     */
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    @Override
    public Page<IBlogSimpleVo> iPageBlogTitle(PageRequest pageRequest,String search) {

        return blogDao.iPageBlogTitle(pageRequest,search);
    }

    @Override
    public Page<IBlogSimpleVo> tPageBlogByType(PageRequest pageRequest, Type type) {

        return blogDao.tPageBlogs(pageRequest,type);
    }

    @Override
    public Page<Blog> pageBolgTag(PageRequest pageRequest, Tag tag) {

        return blogDao.findBlogsByTags(pageRequest,tag);
    }

    /**
     * 分页查询 + 条件查询
     */
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    @Override
    public Page<Blog> listBlog(PageRequest pageRequest, String search) {

        Page<Blog> blogs = blogDao.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(search) && search != null) {
                    predicates.add(cb.like(root.<String>get("title"), "%"+search+"%"));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageRequest);
        return blogs;
    }

    /**
     * 简单分页查询
     */
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    @Override
    public Page<Blog> listBlog(PageRequest pageRequest) {
        return blogDao.findAll(pageRequest);
    }

    /**
     * 博客首页的博客简介
     */
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    @Override
    public Page<IBlogSimpleVo> iPageBlog(PageRequest pageRequest) {

        return blogDao.iPageBlogs(pageRequest);
    }

}