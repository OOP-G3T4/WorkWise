package com.officecleaners.WorkWise.repository;

import com.officecleaners.WorkWise.model.*;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    // Custom queries can be added here if needed
    Optional<Employee> findById(Integer ID);

    ArrayList<Employee> findAll();

}

