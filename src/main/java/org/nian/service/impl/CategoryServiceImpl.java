package org.nian.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.nian.common.CustomException;
import org.nian.common.Result;
import org.nian.entity.Category;
import org.nian.entity.Dish;
import org.nian.entity.Setmeal;
import org.nian.mapper.CategoryMapper;
import org.nian.service.CategoryService;
import org.nian.service.DishService;
import org.nian.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    DishService dishService;

    @Autowired
    SetmealService setmealService;
    @Override
    public Result<String> save_category(Category category) {
        this.save(category);
        return Result.success("新增分类成功");
    }

    @Override
    public Result<Page> page_category(int page, int pageSize, String name) {
        //构造分页构造器
        Page<Category> pageInfo = new Page<>(page, pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //并对查询的结果进行降序排序，根据更新时间
        queryWrapper.orderByDesc(Category::getSort);
        //执行查询
        this.page(pageInfo, queryWrapper);
        return Result.success(pageInfo);
    }

    @Override
    public Result<String> removeById_category(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加dish查询条件，根据分类id进行查询
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        //方便Debug用的
        int count1 = dishService.count(dishLambdaQueryWrapper);
        //查看当前分类是否关联了菜品，如果已经关联，则抛出异常
        if (count1 > 0){
            //已关联菜品，抛出一个业务异常
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加dish查询条件，根据分类id进行查询
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        //查看当前分类是否关联了套餐，如果已经关联，则抛出异常
        if (count2 > 0){
            //已关联套餐，抛出一个业务异常
            throw new CustomException("当前分类下关联了套餐，不能删除");
        }
        //正常删除

        this.removeById(id);
        return Result.success("分类信息删除成功");
    }


    @Override
    public Result<String> update_category(Category category){
        this.updateById(category);
        return Result.success("修改分类信息成功");
    }
}
