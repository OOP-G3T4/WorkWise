package sg.com.officecleanings.workwise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.com.officecleanings.workwise.model.Employee;
import sg.com.officecleanings.workwise.repository.EmployeeRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AdminEmployeeService adminEmployeeService;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
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
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        return employeeRepository.save(employee);
    }

    // Update employee
    public Employee update(Employee employee) {
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
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

    public boolean verifyPassword(int employeeId, String rawPassword) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        return employee.isPresent() && passwordEncoder.matches(rawPassword, employee.get().getPassword());
    }
}