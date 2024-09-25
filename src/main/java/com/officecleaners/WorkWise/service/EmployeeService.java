package com.officecleaners.WorkWise.service;

import com.officecleaners.WorkWise.model.*;
import com.officecleaners.WorkWise.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository EmployeeRepository;

    public List<Employee> getAllEmployees() {
        return EmployeeRepository.findAll();
    }

    public Employee getEmployeeById(Integer id) {
        return EmployeeRepository.findById(id).orElse(null);
    }

    public Employee addEmployee(Employee Employee) {
        return EmployeeRepository.save(Employee);
    }

    public void deleteEmployee(Integer id) {
        EmployeeRepository.deleteById(id);
    }
}
