package com.cb.myblog.repository;

import com.cb.myblog.domain.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
public interface ITypeDao extends JpaRepository<Type,Long>, JpaSpecificationExecutor<Type> {

    /**
     * 判断分类名是否存在
     */
    boolean existsTypeByName(String typeName);

    /**
     * 根据 ID 和 Name 判断是否存在
     */
    boolean existsTypeByIdAndName(Long id, String name);

    /**
     * 根据 ID 判断是否存在
     */
    @Override
    boolean existsById(Long id);

    /**
     * 查询以某种方式排列的分类集合
     */
    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}
