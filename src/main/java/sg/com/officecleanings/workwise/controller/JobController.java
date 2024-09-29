package sg.com.officecleanings.workwise.controller;

import sg.com.officecleanings.workwise.model.Job;
import sg.com.officecleanings.workwise.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public Job createJob(@RequestBody Job job) {
        return jobService.saveJob(job);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable int id) {
        jobService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }
}

