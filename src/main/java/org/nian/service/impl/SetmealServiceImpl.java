package org.nian.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.nian.entity.Setmeal;
import org.nian.mapper.SetmealMapper;
import org.nian.service.SetmealService;
import org.springframework.stereotype.Service;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
}
