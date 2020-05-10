package com.cb.myblog.service;

import com.cb.myblog.domain.Tag;
import com.cb.myblog.domain.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * @author cb
 * @version 1.0
 * @date 2020/3/18 21:05
 */
public interface ITagService {

    Tag saveTag(Tag tag);

    Tag getTag(Long id);

    Page<Tag> listTag(PageRequest pageRequest);

    List<Tag> listTagTop(Integer size);

    List<Tag> listTag(List<Long> ids);

    String updateTag(Long id, String newName);

    boolean deleteTag(Long id);

    boolean existsTagByName(String tagName);

    public boolean existsTagByIdAndName(Long id, String tagName);

    List<Tag> findAllTags();
}
