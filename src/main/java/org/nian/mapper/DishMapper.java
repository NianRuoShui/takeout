package org.nian.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.nian.entity.Dish;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
