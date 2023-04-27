package org.nian.controller;

import lombok.extern.slf4j.Slf4j;
import org.nian.common.Result;
import org.nian.entity.Employee;
import org.nian.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.naming.Name;
import javax.servlet.http.HttpServletRequest;

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

}
