package com.cb.myblog.repository;

import com.cb.myblog.domain.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author cb
 * @version 1.0
 * @date 2020/3/18 21:10
 */
@Repository
public interface ITagDao extends JpaRepository<Tag,Long>, JpaSpecificationExecutor<Tag> {

    /**
     * 判断分类名是否存在
     */
    boolean existsTagByName(String tagName);

    /**
     * 根据 ID 和 Name 判断是否存在
     */
    boolean existsTagByIdAndName(Long id, String name);

    /**
     * 根据 ID 判断是否存在
     */
    @Override
    boolean existsById(Long id);

    /**
     * 查询以某种方式排列的标签集合
     */
    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);
}
