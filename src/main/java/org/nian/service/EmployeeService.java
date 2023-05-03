package org.nian.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.nian.common.Result;
import org.nian.entity.Employee;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
@Service
public interface EmployeeService extends IService<Employee> {
    Result<Employee> login(HttpServletRequest request, @RequestBody Employee employee);
    Result<String> logout(HttpServletRequest request);

    Result<String> save_employee(HttpServletRequest request, @RequestBody Employee employee);

    Result<Page> page_employee(int page, int pageSize, String name);

    Result<String> update_employee(HttpServletRequest request, @RequestBody Employee employee);

    Result<Employee> get_employee_byid(@PathVariable Long id);
}
