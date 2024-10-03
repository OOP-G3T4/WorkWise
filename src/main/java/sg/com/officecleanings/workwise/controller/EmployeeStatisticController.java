package sg.com.officecleanings.workwise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.com.officecleanings.workwise.model.EmployeeStatistic;
import sg.com.officecleanings.workwise.service.EmployeeStatisticService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employee-statistics")
public class EmployeeStatisticController {

    @Autowired
    private EmployeeStatisticService employeeStatisticService;

    @GetMapping
    public List<EmployeeStatistic> getAllEmployeeStatistics() {
        return employeeStatisticService.getAllEmployeeStatistics();
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<EmployeeStatistic> getEmployeeStatisticById(@PathVariable int id) {
    //     Optional<EmployeeStatistic> statistic = employeeStatisticService.getEmployeeStatisticById(id);
    //     return statistic.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    // }

    // @PostMapping
    // public EmployeeStatistic createEmployeeStatistic(@RequestBody EmployeeStatistic employeeStatistic) {
    //     return employeeStatisticService.saveEmployeeStatistic(employeeStatistic);
    // }

    // @PutMapping("/{id}")
    // public ResponseEntity<EmployeeStatistic> updateEmployeeStatistic(@PathVariable int id, @RequestBody EmployeeStatistic employeeStatistic) {
    //     Optional<EmployeeStatistic> existingStatistic = employeeStatisticService.getEmployeeStatisticById(id);
    //     if (existingStatistic.isPresent()) {
    //         employeeStatistic.setStatId(id);
    //         return ResponseEntity.ok(employeeStatisticService.saveEmployeeStatistic(employeeStatistic));
    //     }
    //     return ResponseEntity.notFound().build();
    // }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> deleteEmployeeStatistic(@PathVariable int id) {
    //     employeeStatisticService.deleteEmployeeStatistic(id);
    //     return ResponseEntity.noContent().build();
    // }
}

