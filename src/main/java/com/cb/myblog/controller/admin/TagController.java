package com.cb.myblog.controller.admin;

import com.cb.myblog.domain.Tag;
import com.cb.myblog.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author cb
 * @version 1.0
 * @date 2020/3/18 22:04
 */
@RestController
@RequestMapping("/admin")
public class TagController {
    @Autowired
    private ITagService tagService;


    /**
     * 查询所有标签
     */
    @CrossOrigin
    @GetMapping("/tags")
    public List<Tag> tagAll() {
        return tagService.findAllTags();
    }

    /**
     * 分页查询标签
     */
    @CrossOrigin
    @GetMapping("/tags/{pageNum}/{pageSize}")
    public Page<Tag> tags(@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize) {
        PageRequest p = PageRequest.of(pageNum, pageSize);

        Page<Tag> tags = tagService.listTag(p);
        return tags;
    }

    /**
     * 新增标签名
     */
    @CrossOrigin
    @PostMapping("/tags")
    public String input(@RequestBody Tag tag) {

        if (tag.getName() == null) {
            return "null";
        }
        if (tagService.existsTagByName(tag.getName())) {
            return "exists";
        }

        tagService.saveTag(tag);
        return "success";
    }

    /**
     * 修改标签名
     */
    @CrossOrigin
    @PostMapping("/tags/{id}/{oldName}")
    public String editTag(@PathVariable("id") Long id, @PathVariable("oldName") String oldName, @RequestBody Tag tag) {

        if (!tagService.existsTagByIdAndName(id, oldName)) {
            return "notExists";
        }

        tag.setId(id);
        tagService.saveTag(tag);
        return "success";
    }

    /**
     * 删除标签名
     */
    @CrossOrigin
    @DeleteMapping("/tags/{id}")
    public String deleteTag(@PathVariable String id) {
        System.out.println(id);
        if (tagService.deleteTag(Long.valueOf(id))) {
            return "success";
        }
        return "notExists";
    }

}
