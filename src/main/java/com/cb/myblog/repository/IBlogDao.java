package com.cb.myblog.repository;

import com.cb.myblog.domain.Blog;
import com.cb.myblog.domain.Tag;
import com.cb.myblog.domain.Type;
import com.cb.myblog.vo.IBlogRecommendVo;
import com.cb.myblog.vo.IBlogSimpleVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author cb
 * @version 1.0
 * @date 2020/3/18 21:10
 */
@Repository
public interface IBlogDao extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {

    @Query("select new com.cb.myblog.vo.IBlogRecommendVo(b.id,b.title)  from Blog b where b.flag = 'true'")
    List<IBlogRecommendVo> findTop(Pageable pageable);

    @Query("select new com.cb.myblog.vo.IBlogSimpleVo(b.id,b.title,b.description,b.views,b.createTime,b.commentNum,b.likes,b.type,b.firstPicture,b.user) from Blog b")
    Page<IBlogSimpleVo> iPageBlogs(Pageable pageable);

    /**
     * 游客浏览页根据 title 查询博客
     * @param pageable  分页数据
     * @param search    条件索引
     * @return
     */
    @Query("select new com.cb.myblog.vo.IBlogSimpleVo(b.id,b.title,b.description,b.views,b.createTime,b.commentNum,b.likes,b.type,b.firstPicture,b.user) from Blog b where b.title like %:search%")
    Page<IBlogSimpleVo> iPageBlogTitle(Pageable pageable,@Param("search") String search);

    @Modifying
    @Query("update Blog b set b.views = b.views+1  where id = :id")
    void updateViews(@Param("id") Long id);

    @Modifying
    @Query("update Blog b set b.commentNum = b.commentNum+1  where id = :id")
    void updateCommentNum(@Param("id") Long id);

    @Query("select new com.cb.myblog.vo.IBlogSimpleVo(b.id,b.title,b.description,b.views,b.createTime,b.commentNum,b.likes,b.type,b.firstPicture,b.user) from Blog b where b.type = :type")
    Page<IBlogSimpleVo> tPageBlogs(Pageable pageable,@Param("type") Type type);

    Page<Blog> findBlogsByTags(Pageable pageable, Tag tags);

    /**
     * 查询共有几个年份出现，用倒序排序
     */
    @Query("select " +
            "function('date_format',b.createTime,'%Y-%m') as year " +
            "from Blog b " +
            "group by function('date_format',b.createTime,'%Y-%m')" +
            "order by year desc")
    List<String> findGroupYear();

    @Query("select b from Blog b where function('date_format',b.createTime,'%Y-%m') = ?1 order by b.createTime DESC")
    List<Blog> findByYear(String year);

    @Query("select b from Blog b where function('date_format',b.createTime,'%Y-%m') = :year order by b.createTime DESC")
    Page<Blog> pageFindBlogsByYear(Pageable pageable,@Param("year") String year);
}