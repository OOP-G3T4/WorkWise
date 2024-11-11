package sg.com.officecleanings.workwise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.com.officecleanings.workwise.dto.JobAssignmentDTO;
import sg.com.officecleanings.workwise.model.Employee;
import sg.com.officecleanings.workwise.service.CheckerService;

import java.util.List;

@RestController
@CrossOrigin(origins = "${cors.allowedOrigins}")
@RequestMapping("/api/checker")
public class CheckerController {

    List<JobAssignmentDTO> assignments = List.of(
            new JobAssignmentDTO(4, List.of(3, 5), "Job 4 requires 2 employees for a 3-hour duration starting at 11:00 AM. Charlie Mopster (Employee 3) and Harriet Hoover (Employee 5) are the best candidates as they are available during the job's scheduled time, not on leave, and have not exceeded their maximum working hours. They also have sufficient time to travel to the job location at 900 Dunearn Rd, Central, with the shortest travel times from their respective home addresses. Both employees can take their lunch break before or after the job, satisfying the meal break requirement."),
            new JobAssignmentDTO(8, List.of(5), "Job 8 requires 1 employee for a 4-hour duration starting at 11:00 AM. Harriet Hoover (Employee 5) is the best candidate as she is available during the job's scheduled time, not on leave, and has not exceeded her maximum working hours. She has sufficient time to travel to the job location at 456 Jurong West Street, West, with the shortest travel time from her home address. Harriet can take her lunch break before or after the job, satisfying the meal break requirement."),
            new JobAssignmentDTO(14, List.of(1), "Marky McFly is available on 2024-11-11 at 08:00:00 for a 4-hour job. He is not on leave and has not exceeded the maximum allowed working hours. He has a non-working day in the week, and he can take a lunch break after the job. His home is in the East, which is close to the job location at 767 Pasir Ris Street 71, ensuring the shortest travel time."),
            new JobAssignmentDTO(9, List.of(6), "Darryl So is available on 2024-11-11 at 09:00:00 for a 3-hour job. He is not on leave and has not exceeded the maximum working hours. He has a non-working day in the week and can have lunch after the job. His home is in the North-East, which is reasonably close to the job location at 123 Changi Road, ensuring a short travel time."),
            new JobAssignmentDTO(10, List.of(4, 7), "Lenny Laundry and Gwen Low are both available on 2024-11-11 at 12:00:00 for a 3-hour job requiring 2 employees. Neither is on leave or has exceeded the maximum working hours, and both have non-working days in the week. They can have lunch before the job. Lenny's home is in the North, and Gwen's home is in the North-East, both reasonably close to the job location at 11 Mount Faber Rd, ensuring short travel times."),
            new JobAssignmentDTO(11, List.of(5), "Harriet Hoover is available on 2024-11-11 at 14:30:00 for a 4-hour job. She is not on leave and has not exceeded the maximum working hours. She has a non-working day in the week and can have a dinner break after the job. Her home is in the West, close to the job location at 456 Jurong West Street, ensuring the shortest travel time."),
            new JobAssignmentDTO(13, List.of(7), "Gwen Low is available on 2024-11-11 at 18:00:00 for a 4-hour job. She is not on leave and has not exceeded the maximum working hours. She has a non-working day in the week and can have a dinner break before the job. Her home is in the North-East, reasonably close to the job location at 767 Pasir Ris Street 71, ensuring a short travel time."),
            new JobAssignmentDTO(12, List.of(3, 1), "Charlie Mopster and Marky McFly are both available on 2024-11-11 at 20:00:00 for a 3-hour job requiring 2 employees. Neither is on leave or has exceeded the maximum working hours, and both have non-working days in the week. They can have dinner before the job. Charlie's home is in Central, and Marky's home is in the East, both reasonably close to the job location at 900 Dunearn Rd, ensuring short travel times.")
    );

    @Autowired
    private CheckerService checkerService;

    // it should run validateBatchJobAssignments with the assignments list
    // return boolean
    @GetMapping("/validate")
    public boolean validateBatchJobAssignments() {
        return checkerService.validateBatchJobAssignments(assignments).getKey();
    }


    @GetMapping("/job/{id}")
    public ResponseEntity<List<Employee>> getAvailableEmployeesForJob(@PathVariable int id) {
        List<Employee> employees = checkerService.getAvailableEmployeesForJob(id);
        return ResponseEntity.ok(employees);
    }
}
