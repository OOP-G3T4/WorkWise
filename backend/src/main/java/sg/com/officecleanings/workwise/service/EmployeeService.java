package sg.com.officecleanings.workwise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.com.officecleanings.workwise.model.Employee;
import sg.com.officecleanings.workwise.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    private AdminEmployeeService adminEmployeeService;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // Fetch all employees
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    // Fetch employee by ID, now returning Optional
    public Optional<Employee> getById(int employeeId) {
        return employeeRepository.findById(employeeId);
    }

    // Insert new employee
    public Employee insert(Employee employee) {
        return employeeRepository.save(employee);
    }

    // Update employee
    public Employee update(Employee employee) {
        return employeeRepository.save(employee);
    }

    // Delete employee by ID
    public void delete(int employeeId) {
        adminEmployeeService.deleteByEmployeeId(employeeId);
        employeeRepository.deleteById(employeeId);
    }

    // Check if employee exists by ID
    public boolean existsById(int employeeId) {
        return employeeRepository.existsById(employeeId);
    }
}