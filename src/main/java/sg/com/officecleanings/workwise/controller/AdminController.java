package sg.com.officecleanings.workwise.controller;

import sg.com.officecleanings.workwise.model.Admin;
import sg.com.officecleanings.workwise.model.Employee;
import sg.com.officecleanings.workwise.service.AdminEmployeeService;
import sg.com.officecleanings.workwise.service.AdminService;
import org.springframework.http.HttpStatus;
import org.hibernate.internal.util.collections.ConcurrentReferenceHashMap.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminEmployeeService adminEmployeeService;

    @GetMapping
    public List<Admin> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable int id) {
        Optional<Admin> admin = adminService.getAdminById(id);
        return admin.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Admin createAdmin(@RequestBody Admin admin) {
        return adminService.saveAdmin(admin);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable int id, @RequestBody Admin adminDetails) {
        Optional<Admin> admin = adminService.getAdminById(id);
        if (admin.isPresent()) {
            adminDetails.setAdminId(id);
            return ResponseEntity.ok(adminService.saveAdmin(adminDetails));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAdmin(@PathVariable int id) {
        try {
            Optional<Admin> adminOptional = adminService.getAdminById(id);
            if (adminOptional.isEmpty()) {
                String errorMessage = "Admin with ID " + id + " not found.";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
            }
            adminService.deleteAdmin(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = "An error occurred while deleting the admin.";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @GetMapping("/{id}/employees")
    public ResponseEntity<List<Employee>> getEmployeesByAdminId(@PathVariable int id) {
        List<Employee> employees = adminEmployeeService.findEmployeesByAdminId(id);
        if (employees.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(employees);
    }
}
