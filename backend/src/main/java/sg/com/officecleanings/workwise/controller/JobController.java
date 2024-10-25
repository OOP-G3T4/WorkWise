package sg.com.officecleanings.workwise.controller;

import sg.com.officecleanings.workwise.model.Employee;
import sg.com.officecleanings.workwise.model.Job;
import sg.com.officecleanings.workwise.service.JobService;
import sg.com.officecleanings.workwise.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/api/job")
public class JobController {

    @Autowired
    private JobService jobService;

    @Autowired
    private EmployeeService EmployeeService;

    @GetMapping
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable int id) {
        Optional<Job> job = jobService.getJobById(id);
        return job.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody Job job) {
        try {
            Job createdJob = jobService.saveJob(job);
            return new ResponseEntity<>(createdJob, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable int id, @RequestBody Job jobDetails) {
        try {
            Optional<Job> job = jobService.getJobById(id);
            if (job.isPresent()) {
                jobDetails.setJobId(id);
                return ResponseEntity.ok(jobService.saveJob(jobDetails));
            }
            return ResponseEntity.notFound().build();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable int id) {
        try {
            Optional<Job> jobOptional = jobService.getJobById(id);
            if (jobOptional.isEmpty()) {
                String errorMessage = "Job with ID " + id + " not found.";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
            }
            jobService.deleteJob(id);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            String errorMessage = "An error occurred while deleting the job: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Job>> getJobsByEmployeeId(@PathVariable int employeeId) {
        try {
            List<Job> jobs = jobService.getJobsByEmployeeId(employeeId);
            if (jobs.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(jobs, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{jobId}/employees")
    public ResponseEntity<List<Employee>> getEmployeesByJobId(@PathVariable int jobId) {
        try {
            List<Employee> employees = jobService.getEmployeesByJobId(jobId);
            if (employees.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/by-day")
    public ResponseEntity<List<Job>> getJobsByDay(@RequestParam("date") String dateStr) {
        try {
            Date date = Date.valueOf(dateStr);
            List<Job> jobs = jobService.getJobsByDay(date);
            return ResponseEntity.ok(jobs);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/by-week")
    public ResponseEntity<List<Job>> getJobsByWeek(@RequestParam("date") String dateStr) {
        try {
            LocalDate date = LocalDate.parse(dateStr);
            List<Job> jobs = jobService.getJobsByWeek(date);
            return ResponseEntity.ok(jobs);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/by-month")
    public ResponseEntity<List<Job>> getJobsByMonth(@RequestParam("date") String dateStr) {
        try {
            LocalDate date = LocalDate.parse(dateStr);
            List<Job> jobs = jobService.getJobsByMonth(date);
            return ResponseEntity.ok(jobs);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<Job>> getJobsByStatus(@RequestParam("status") Job.Status status) {
        try {
            List<Job> jobs = jobService.getJobsByStatus(status);
            return ResponseEntity.ok(jobs);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    //Get all pending jobs in the next week
    @GetMapping("/pending-jobs")
    public ResponseEntity<List<Job>> getPendingJobsInNextWeek(@RequestParam("date") String dateStr) {
        try {
            LocalDate date = LocalDate.parse(dateStr);
            List<Job> jobs = jobService.getPendingJobsInNextWeek(date);
            return ResponseEntity.ok(jobs);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
