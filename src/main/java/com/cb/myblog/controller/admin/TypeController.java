package com.cb.myblog.controller.admin;

import com.cb.myblog.domain.Type;
import com.cb.myblog.service.ITypeService;
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
public class TypeController {
    @Autowired
    private ITypeService typeService;

    /**
     * 查询所有分类
     */
    @CrossOrigin
    @GetMapping("/types")
    public List<Type> typeAll() {
        return typeService.findAllTypes();
    }

    /**
     * 分页查询分类
     */
    @CrossOrigin
    @GetMapping("/types/{pageNum}/{pageSize}")
    public Page<Type> types(@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize) {
        PageRequest p = PageRequest.of(pageNum, pageSize);

        Page<Type> types = typeService.listType(p);
        return types;
    }

    /**
     * 新增分类名
     */
    @CrossOrigin
    @PostMapping("/types")
    public String input(@RequestBody Type type) {

        if (type.getName() == null) {
            return "null";
        }
        if (typeService.existsTypeByName(type.getName())) {
            return "exists";
        }

        typeService.saveType(type);
        return "success";
    }

    /**
     * 修改分类名
     */
    @CrossOrigin
    @PostMapping("/types/{id}/{oldName}")
    public String editType(@PathVariable("id") Long id, @PathVariable("oldName") String oldName, @RequestBody Type type) {

        if (!typeService.existsTypeByIdAndName(id, oldName)) {
            return "notExists";
        }

        type.setId(id);
        typeService.saveType(type);
        return "success";
    }

    /**
     * 删除分类名
     */
    @CrossOrigin
    @DeleteMapping("/types/{id}")
    public String deleteType(@PathVariable String id) {
        System.out.println(id);
        if (typeService.deleteType(Long.valueOf(id))) {
            return "success";
        }
        return "notExists";
    }

}
