package org.nian.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.nian.common.Result;
import org.nian.entity.Category;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService extends IService<Category> {
    Result<String> save_category(Category category);

    Result<Page> page_category(int page, int pageSize, String name);

    Result<String> removeById_category(Long id);

    Result<String> update_category(Category category);

}
