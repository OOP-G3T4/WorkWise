package sg.com.officecleanings.workwise.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.com.officecleanings.workwise.model.Employee;
import sg.com.officecleanings.workwise.service.EmployeeService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
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
                .map(ResponseEntity::ok)  // Use map to return 200 OK if found
                .orElse(ResponseEntity.notFound().build());    // Return 404 Not Found if not found
    }

    @PostMapping
    public ResponseEntity<Employee> insert(@Valid @RequestBody Employee employee) {
        Employee newEmployee = employeeService.insert(employee);
        return ResponseEntity.created(URI.create("/employees/" + newEmployee.getId())).body(newEmployee); // Fixed getId()
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<Employee> update(@PathVariable int employeeId, @Valid @RequestBody Employee employee) {
        if (employeeService.existsById(employeeId)) {
            employee.setId(employeeId);  // Ensure the path ID is set to the employee object
            Employee updatedEmployee = employeeService.update(employee);
            return ResponseEntity.ok(updatedEmployee);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> delete(@PathVariable int employeeId) {
        if (employeeService.existsById(employeeId)) {
            employeeService.delete(employeeId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}