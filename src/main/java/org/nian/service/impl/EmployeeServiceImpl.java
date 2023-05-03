package org.nian.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.nian.common.Result;
import org.nian.entity.Employee;
import org.nian.mapper.EmployeeMapper;
import org.nian.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import javax.servlet.http.HttpServletRequest;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
    @Override
    public Result<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        // 对用户密码进行 MD5 加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //这部分就是MyBatis Plus
        //根据用户名查询用户
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<>();
        //Employee::getUsername就相当于创建一个Employee对象并调用其getUsername方法
        //wrapper.eq("实体类::查询字段", "条件值"); //相当于where条件
        lqw.eq(Employee::getUsername, employee.getUsername());
        // 获取查询到的用户
        Employee emp = this.getOne(lqw);
        // 验证用户是否存在
        if (emp == null) {
            return Result.error("登陆失败");
        }
        if (!emp.getPassword().equals(password)) {
            return Result.error("登录失败");
        }
        if (emp.getStatus() == 0) {
            return Result.error("该用户已被禁用");
        }
        //// 将用户 ID 存储到 Session 中
        request.getSession().setAttribute("employee",emp.getId());
        return Result.success(emp);
    }

    @Override
    public Result<String> logout(HttpServletRequest request) {
        // 从 Session 中移除用户信息
        request.getSession().removeAttribute("employee");
        return Result.success("退出成功");
    }

    @Override
    public Result<String> save_employee(HttpServletRequest request, Employee employee) {
        // 为新用户设置初始密码，并进行 MD5 加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
//        LocalDateTime now = LocalDateTime.now();
//        employee.setCreateTime(now);
//        employee.setUpdateTime(now);
//        Long createId = (Long) request.getSession().getAttribute("employee");
//        employee.setCreateUser(createId);
//        employee.setUpdateUser(createId);
        // 保存新用户,this是EmployeeServiceImpl的实例，有save方法
        this.save(employee);

        return Result.success("添加成功");
    }

    @Override
    public Result<Page> page_employee(int page, int pageSize, String name){
        //构造分页构造器
        Page<Employee> pageInfo = new Page<>(page, pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        //添加过滤条件（当我们没有输入name时，就相当于查询所有了）
        wrapper.like(!(name == null || "".equals(name)), Employee::getName, name);
        //并对查询的结果进行降序排序，根据更新时间
        wrapper.orderByDesc(Employee::getUpdateTime);
        //执行查询
        this.page(pageInfo, wrapper);
        return Result.success(pageInfo);
    }
    @Override
    public Result<String> update_employee(HttpServletRequest request, @RequestBody Employee employee){
//        Long id = (Long) request.getSession().getAttribute("employee");
//        employee.setUpdateUser(id);
//        employee.setUpdateTime(LocalDateTime.now());
        this.updateById(employee);
        return Result.success("员工信息修改成功");
    }

    @Override
    public Result<Employee> get_employee_byid(@PathVariable Long id){
        Employee employee = this.getById(id);
        if (employee != null) {
            return Result.success(employee);
        }
        return Result.error("未查询到该员工信息");
    }

}
