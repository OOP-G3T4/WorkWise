package sg.com.officecleanings.workwise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.com.officecleanings.workwise.model.Employee;
import sg.com.officecleanings.workwise.service.EmployeeService;

import java.util.List;

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
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.ok(employees); // 200 OK with the list of employees
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> getById(@PathVariable int employeeId) {
        Employee employee = employeeService.getById(employeeId);
        if (employee == null) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
        return ResponseEntity.ok(employee); // 200 OK with the employee
    }

    @PostMapping
    public ResponseEntity<Employee> insert(@RequestBody Employee employee) {
        Employee newEmployee = employeeService.insert(employee);
        return ResponseEntity.ok(newEmployee); // 200 OK with the new employee
    }

    @PutMapping
    public ResponseEntity<Employee> update(@RequestBody Employee employee) {
        Employee updatedEmployee = employeeService.update(employee);
        return ResponseEntity.ok(updatedEmployee); // 200 OK with the updated employee
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> delete(@PathVariable int employeeId) {
        employeeService.delete(employeeId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
