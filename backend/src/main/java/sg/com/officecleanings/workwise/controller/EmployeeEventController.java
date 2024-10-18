package sg.com.officecleanings.workwise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.com.officecleanings.workwise.model.EmployeeEvent;
import sg.com.officecleanings.workwise.service.EmployeeEventService;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

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

    //Get all employee events in a week
    @GetMapping("/week")
    public ResponseEntity<List<EmployeeEvent>> getEmployeeEventsByWeek(@RequestParam("date") String dateStr) {
        try {
            LocalDate date = LocalDate.parse(dateStr);
            List<EmployeeEvent> events = employeeEventService.getEmployeeEventsByWeek(date);
            return ResponseEntity.ok(events);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping
    public EmployeeEvent createEmployeeEvent(@RequestBody EmployeeEvent employeeEvent) {
        return employeeEventService.saveEmployeeEvent(employeeEvent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeEvent> updateEmployeeEvent(@PathVariable int id,
            @RequestBody EmployeeEvent employeeEvent) {
        Optional<EmployeeEvent> existingEvent = employeeEventService.getEmployeeEventById(id);
        if (existingEvent.isPresent()) {
            employeeEvent.setEventId(id);
            return ResponseEntity.ok(employeeEventService.saveEmployeeEvent(employeeEvent));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeEvent(@PathVariable int id) {
        try {
            Optional<EmployeeEvent> eventOptional = employeeEventService.getEmployeeEventById(id);
            if (eventOptional.isEmpty()) {
                String errorMessage = "Employee event with ID " + id + " not found.";
                return ResponseEntity.status(404).body(errorMessage);
            }
            employeeEventService.deleteEmployeeEvent(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();

        }
    }
}
