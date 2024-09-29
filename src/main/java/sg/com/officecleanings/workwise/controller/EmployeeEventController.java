package sg.com.officecleanings.workwise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.com.officecleanings.workwise.model.EmployeeEvent;
import sg.com.officecleanings.workwise.service.EmployeeEventService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employee-events")
public class EmployeeEventController {

    @Autowired
    private EmployeeEventService employeeEventService;

    @GetMapping
    public List<EmployeeEvent> getAllEmployeeEvents() {
        return employeeEventService.getAllEmployeeEvents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeEvent> getEmployeeEventById(@PathVariable int id) {
        Optional<EmployeeEvent> event = employeeEventService.getEmployeeEventById(id);
        return event.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public EmployeeEvent createEmployeeEvent(@RequestBody EmployeeEvent employeeEvent) {
        return employeeEventService.saveEmployeeEvent(employeeEvent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeEvent> updateEmployeeEvent(@PathVariable int id, @RequestBody EmployeeEvent employeeEvent) {
        Optional<EmployeeEvent> existingEvent = employeeEventService.getEmployeeEventById(id);
        if (existingEvent.isPresent()) {
            employeeEvent.setEventId(id);
            return ResponseEntity.ok(employeeEventService.saveEmployeeEvent(employeeEvent));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeEvent(@PathVariable int id) {
        employeeEventService.deleteEmployeeEvent(id);
        return ResponseEntity.noContent().build();
    }
}
