package sg.com.officecleanings.workwise.controller;

import sg.com.officecleanings.workwise.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = "${cors.allowedOrigins}")
@RequestMapping("/api/subscription")
public class SubscriptionController {

    @Autowired
    private JobService jobService;

    @GetMapping("/createFromActiveSubscriptions")
    public ResponseEntity<String> createFromActiveSubscriptions() {
        System.out.println("Creating jobs from active subscriptions function running...");
        boolean status = jobService.createJobsFromActiveSubscriptions();
        return ResponseEntity.ok("status: " + status);
    }
}