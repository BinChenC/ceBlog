package com.cb.myblog.service;

import com.cb.myblog.domain.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * @author cb
 * @version 1.0
 * @date 2020/3/18 21:05
 */
public interface ITypeService {

    Type saveType(Type type);

    List<Type> listTypeTop(Integer size);

    Type getType(Long id);

    Page<Type> listType(PageRequest pageRequest);

    String updateType(Long id, String newName);

    boolean deleteType(Long id);

    boolean existsTypeByName(String typeName);

    public boolean existsTypeByIdAndName(Long id,String typeName);

    List<Type> findAllTypes();
}
