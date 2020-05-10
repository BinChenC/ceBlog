package com.cb.myblog.service.impl;

import com.cb.myblog.domain.Tag;
import com.cb.myblog.repository.ITagDao;
import com.cb.myblog.service.ITagService;
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
public class TagServiceImpl implements ITagService {

    @Autowired
    private ITagDao tagDao;

    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    @Override
    public List<Tag> listTag(List<Long> ids) {
        return tagDao.findAllById(ids);
    }

    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    @Override
    public List<Tag> findAllTags() {
        return tagDao.findAll();
    }

    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    @Override
    public Tag getTag(Long id) {
        return tagDao.findById(id).get();
    }


    @Override
    public Tag saveTag(Tag tag) {
        return tagDao.save(tag);
    }

    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    @Override
    public Page<Tag> listTag(PageRequest pageRequest) {

        return tagDao.findAll(pageRequest);
    }

    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "blogs.size");
        PageRequest pageRequest = PageRequest.of(0, size, sort);
        return tagDao.findTop(pageRequest);
    }

    @Override
    public String updateTag(Long id, String newName) {
        Tag t = tagDao.getOne(id);
        if (t != null) {
            t.setName(newName);
            tagDao.save(t);
            System.out.println(t);
            return "success";
        }
        return "error";
    }

    /**
     * 根据 Id 删除分类名
     */
    @Override
    public boolean deleteTag(Long id) {
        if (tagDao.existsById(id)) {
            tagDao.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * 根据分类名判断是否存在
     */
    @Override
    public boolean existsTagByName(String tagName){
        return tagDao.existsTagByName(tagName);
    }

    /**
     * 根据 Id 和 Name 判断是否存在
     */
    @Override
    public boolean existsTagByIdAndName(Long id,String tagName){
        return tagDao.existsTagByIdAndName(id,tagName);
    }


}
