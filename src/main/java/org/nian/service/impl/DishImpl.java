package org.nian.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.nian.entity.Dish;
import org.nian.mapper.DishMapper;
import org.nian.service.DishService;
import org.springframework.stereotype.Service;

@Service
public class DishImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
}
