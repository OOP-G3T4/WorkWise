package sg.com.officecleanings.workwise.controller;

import jakarta.validation.Valid;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.com.officecleanings.workwise.model.Employee;
import sg.com.officecleanings.workwise.dto.EmployeeRequest;
import sg.com.officecleanings.workwise.model.Admin;
import sg.com.officecleanings.workwise.model.AdminEmployee;
import sg.com.officecleanings.workwise.service.EmployeeService;
import sg.com.officecleanings.workwise.service.AdminService;
import sg.com.officecleanings.workwise.service.AdminEmployeeService;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "${cors.allowedOrigins}")
@RequestMapping("/api/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    private AdminEmployeeService AdminEmployeeService;

    @Autowired
    private AdminService AdminService;

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
    public ResponseEntity<Employee> insert(@Valid @RequestBody EmployeeRequest employeeRequest) {

        Integer adminId = employeeRequest.getAdminId();
        Optional<Admin> adminOptional = AdminService.getAdminById(adminId);
        if (adminOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Employee newEmployee = new Employee();
        newEmployee.setName(employeeRequest.getName());
        newEmployee.setHomeAddress(employeeRequest.getHomeAddress());
        newEmployee.setPhoneNumber(employeeRequest.getPhoneNumber());
        newEmployee.setShortBio(employeeRequest.getShortBio());
        newEmployee.setEmail(employeeRequest.getEmail());
        newEmployee.setAlBalance(employeeRequest.getAlBalance());
        newEmployee.setMcBalance(employeeRequest.getMcBalance());

        newEmployee = employeeService.insert(newEmployee);
        AdminEmployee adminEmployee = new AdminEmployee(adminOptional.get(), newEmployee);
        AdminEmployeeService.save(adminEmployee);
        return ResponseEntity.created(URI.create("/employees/" + newEmployee.getEmployeeId())).body(newEmployee); // Fixed
                                                                                                                  // getId()
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<Employee> update(@PathVariable int employeeId,
            @Valid @RequestBody EmployeeRequest employeeRequest) {
        if (!employeeService.existsById(employeeId)) {
            return ResponseEntity.notFound().build();
        }
        Employee existingEmployee = employeeService.getById(employeeId).orElseThrow();
        existingEmployee.setName(employeeRequest.getName());
        existingEmployee.setHomeAddress(employeeRequest.getHomeAddress());
        existingEmployee.setPhoneNumber(employeeRequest.getPhoneNumber());
        existingEmployee.setShortBio(employeeRequest.getShortBio());
        existingEmployee.setEmail(employeeRequest.getEmail());
        existingEmployee.setAlBalance(employeeRequest.getAlBalance());
        existingEmployee.setMcBalance(employeeRequest.getMcBalance());

        Integer newAdminId = employeeRequest.getAdminId();
        if (newAdminId != null) {
            Optional<Admin> adminOptional = AdminService.getAdminById(newAdminId);
            if (adminOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            AdminEmployeeService.deleteByEmployeeId(employeeId);
            AdminEmployee adminEmployee = new AdminEmployee(adminOptional.get(), existingEmployee);
            AdminEmployeeService.save(adminEmployee);
        }
        Employee updatedEmployee = employeeService.update(existingEmployee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<String> delete(@PathVariable int employeeId) {
        try {
            Optional<Employee> employeeOptional = employeeService.getById(employeeId);
            if (employeeOptional.isEmpty()) {
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

    @GetMapping("/{employeeId}/admin")
    public ResponseEntity<Admin> getAdminByEmployeeId(@PathVariable int employeeId) {
        Optional<Admin> admin = AdminEmployeeService.findAdminByEmployeeId(employeeId);
        return admin.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}