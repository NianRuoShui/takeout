package org.nian.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.nian.common.Result;
import org.nian.entity.Employee;
import org.nian.mapper.EmployeeMapper;
import org.nian.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import javax.servlet.http.HttpServletRequest;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
    @Override
    public Result<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        //MD5加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //这部分就是MP
        //根据用户名查询用户
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<>();
        //Employee::getUsername就相当于创建一个Employee对象并调用其getUsername方法
        //wrapper.eq("实体类::查询字段", "条件值"); //相当于where条件
        lqw.eq(Employee::getUsername, employee.getUsername());

        Employee emp = this.getOne(lqw);
        if (emp == null) {
            return Result.error("登陆失败");
        }
        if (!emp.getPassword().equals(password)) {
            return Result.error("登录失败");
        }
        if (emp.getStatus() == 0) {
            return Result.error("该用户已被禁用");
        }
        //存个Session，只存个id就行了
        request.getSession().setAttribute("employee",emp.getId());
        return Result.success(emp);
    }

    @Override
    public Result<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
        return Result.success("退出成功");
    }
}
