package sg.com.officecleanings.workwise.controller;

import jakarta.validation.Valid;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.com.officecleanings.workwise.model.Employee;
import sg.com.officecleanings.workwise.service.EmployeeService;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAll() {
        List<Employee> employees = employeeService.getAll();
        if (employees.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> getById(@PathVariable int employeeId) {
        return employeeService.getById(employeeId)
                .map(ResponseEntity::ok) // Use map to return 200 OK if found
                .orElse(ResponseEntity.notFound().build()); // Return 404 Not Found if not found
    }

    @PostMapping
    public ResponseEntity<Employee> insert(@Valid @RequestBody Employee employee) {
        Employee newEmployee = employeeService.insert(employee);
        return ResponseEntity.created(URI.create("/employees/" + newEmployee.getEmployeeId())).body(newEmployee); // Fixed
                                                                                                                  // getId()
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<Employee> update(@PathVariable int employeeId, @Valid @RequestBody Employee employee) {
        if (employeeService.existsById(employeeId)) {
            employee.setEmployeeId(employeeId); // Ensure the path ID is set to the employee object
            Employee updatedEmployee = employeeService.update(employee);
            return ResponseEntity.ok(updatedEmployee);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<String> delete(@PathVariable int employeeId) {
        try {
            Optional<Employee> employeeOptional = employeeService.getById(employeeId);
            if (!employeeOptional.isPresent()) {
                String errorMessage = "Employee with ID " + employeeId + " not found.";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
            }
            employeeService.delete(employeeId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}