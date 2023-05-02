package org.nian.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.nian.common.Result;
import org.nian.entity.Employee;
import org.nian.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestController
@Slf4j
@RequestMapping("/employee")
public class EmployeeController {
    //注入的是实现类对象，接收的接口；理解为多态
    //@Service("xx")
    //在Service接口有多个ServiceImpt实现类的情况，就需要指定参数名来选择哪个ServiceImpt实现类了@Resource(name="xx")
    @Resource
    private EmployeeService employeeService;
    @PostMapping("/login")
    public Result<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        return employeeService.login(request, employee);
    }

    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request){
        return employeeService.logout(request);
    }

    @PostMapping
    public Result<String> save(HttpServletRequest request, @RequestBody Employee employee){
        return employeeService.save_employee(request, employee);
    }
    //page来自mp自带
    @GetMapping("/page")
    public Result<Page> page(int page, int pageSize, String name) {
        return employeeService.page_employee(page, pageSize, name);
    }

    @PutMapping
    public Result<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        return employeeService.update_employee(request, employee);
    }

    @GetMapping("/{id}")
    public Result<Employee> getById(@PathVariable Long id){
        return employeeService.get_employee_byid(id);
    }

}
