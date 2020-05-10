package com.cb.myblog.service.impl;

import com.cb.myblog.domain.Type;
import com.cb.myblog.repository.ITypeDao;
import com.cb.myblog.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author cb
 * @version 1.0
 * @date 2020/3/18 21:09
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TypeServiceImpl implements ITypeService{

    @Autowired
    private ITypeDao typeDao;

    @Override
    public Type saveType(Type type) {
        return typeDao.save(type);
    }

    @Override
    public List<Type> listTypeTop(Integer size) {

        Sort sort = Sort.by(Sort.Direction.DESC, "blogs.size");
        PageRequest pageRequest = PageRequest.of(0, size,sort);

        return typeDao.findTop(pageRequest);
    }

    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    @Override
    public Type getType(Long id) {
        return typeDao.findById(id).get();
    }

    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    @Override
    public List<Type> findAllTypes() {
        return typeDao.findAll();
    }

    @Override
    public Page<Type> listType(PageRequest pageRequest) {
        return typeDao.findAll(pageRequest);
    }

    @Override
    public String updateType(Long id, String newName) {
        Type t = typeDao.getOne(id);
        if (t != null) {
            t.setName(newName);
            typeDao.save(t);
            System.out.println(t);
            return "success";
        }
        return "error";
    }

    /**
     * 根据 Id 删除分类名
     */
    @Override
    public boolean deleteType(Long id) {
        if (typeDao.existsById(id)) {
            typeDao.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * 根据分类名判断是否存在
     */
    @Override
    public boolean existsTypeByName(String typeName){
        return typeDao.existsTypeByName(typeName);
    }

    /**
     * 根据 Id 和 Name 判断是否存在
     */
    @Override
    public boolean existsTypeByIdAndName(Long id,String typeName){
        return typeDao.existsTypeByIdAndName(id,typeName);
    }


}
