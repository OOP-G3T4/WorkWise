package sg.com.officecleanings.workwise.controller;

import sg.com.officecleanings.workwise.model.Job;
import sg.com.officecleanings.workwise.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/job")
public class JobController {

    @Autowired
    private JobService jobService;

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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while creating the job.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable int id, @RequestBody Job jobDetails) {
        Optional<Job> job = jobService.getJobById(id);
        if (job.isPresent()) {
            jobDetails.setJobId(id);
            return ResponseEntity.ok(jobService.saveJob(jobDetails));
        }
        return ResponseEntity.notFound().build();
    }

    // Returns 204 No Content if the job is successfully deleted without the success msg 
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable int id) {
        try {
            Optional<Job> jobOptional = jobService.getJobById(id);
            if (!jobOptional.isPresent()) {
                String errorMessage = "Job with ID " + id + " not found.";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
            }
            jobService.deleteJob(id);

            String successMessage = "Job with ID " + id + " has been successfully deleted.";
            System.out.println("Returning response: " + successMessage);
            return ResponseEntity.ok(successMessage);

        } catch (Exception e) {
            String errorMessage = "An error occurred while deleting the job: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

}
