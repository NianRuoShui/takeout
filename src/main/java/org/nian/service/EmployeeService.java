package org.nian.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.nian.common.Result;
import org.nian.entity.Employee;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

public interface EmployeeService extends IService<Employee> {
    public Result<Employee> login(HttpServletRequest request, @RequestBody Employee employee);
    public Result<String> logout(HttpServletRequest request);
}
