package com.officecleaners.WorkWise.controller;

import com.officecleaners.WorkWise.model.*;
import com.officecleaners.WorkWise.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Employees")
public class EmployeeController {

    private final EmployeeService EmployeeService;

    @Autowired
    public EmployeeController(EmployeeService EmployeeService) {
        this.EmployeeService = EmployeeService;
    }

    @GetMapping("/all")
    public List<Employee> getAllEmployees() {
        return EmployeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Integer id) {
        return EmployeeService.getEmployeeById(id);
    }

    @PostMapping("/add")
    public Employee createEmployee(@RequestBody Employee Employee) {
        return EmployeeService.addEmployee(Employee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Integer id) {
        EmployeeService.deleteEmployee(id);
    }
}
