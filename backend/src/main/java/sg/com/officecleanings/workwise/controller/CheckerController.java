package sg.com.officecleanings.workwise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.com.officecleanings.workwise.model.Employee;
import sg.com.officecleanings.workwise.service.CheckerService;

import java.util.List;

@RestController
@CrossOrigin(origins = "${cors.allowedOrigins}")
@RequestMapping("/api/checker")
public class CheckerController {

    @Autowired
    private CheckerService checkerService;


    @GetMapping("/job/{id}")
    public ResponseEntity<List<Employee>> getAvailableEmployeesForJob(@PathVariable int id) {
        List<Employee> employees = checkerService.getAvailableEmployeesForJob(id);
        return ResponseEntity.ok(employees);
    }
}
