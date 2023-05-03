package org.nian.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.nian.common.Result;
import org.nian.entity.Category;
import org.nian.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;
    @PostMapping
    public Result<String> save(@RequestBody Category category) {
        return categoryService.save_category(category);
    }

    @GetMapping("/page")
    public Result<Page> page(int page, int pageSize, String name) {
        return categoryService.page_category(page, pageSize, name);
    }

    @DeleteMapping
    private Result<String> delete(Long ids) {
        return categoryService.removeById_category(ids);
    }

    @PutMapping
    public Result<String> update(@RequestBody Category category) {
        return categoryService.update_category(category);
    }


}
