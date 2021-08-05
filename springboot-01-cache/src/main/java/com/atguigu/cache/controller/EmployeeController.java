package com.atguigu.cache.controller;

import com.atguigu.cache.bean.Employee;
import com.atguigu.cache.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController  //内部有ResponseBody所以返回的是json数据
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/emp/{id}")
    public Employee getEmployee(@PathVariable("id") Integer id){
        Employee emp = employeeService.getEmp(id);
        return emp;
    }

    //http://localhost:8080/emp?id=1&lastName=zhangsan&gender=0
    @GetMapping("/emp")
    public Employee update(Employee employee){
        Employee emp = employeeService.updateEmp(employee);
        return emp;
    }

    //localhost:8080/delemp?id=1
    @GetMapping("/delemp")
    public String deleteEmp(Integer id){
        employeeService.deleteEmp(id);
        return "success";
    }

    //测试caching
    @GetMapping("/emp/lastName/{lastName}")
    public Employee getEmpByLastName(@PathVariable("lastName") String lastName){
        Employee emp = employeeService.getEmpByLastName(lastName);
        return emp;
    }

}
