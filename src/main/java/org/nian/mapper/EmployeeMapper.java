package org.nian.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.nian.entity.Employee;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee>{

}
