package sg.com.officecleanings.workwise.controller;

import sg.com.officecleanings.workwise.service.JobService;
import sg.com.officecleanings.workwise.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import sg.com.officecleanings.workwise.model.Job;
import sg.com.officecleanings.workwise.model.Subscription;

import java.util.List;
@RestController
@CrossOrigin(origins = "${cors.allowedOrigins}")
@RequestMapping("/api/subscription")
public class SubscriptionController {

    @Autowired
    private JobService jobService;

    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping("/createFromActiveSubscriptions")
    public ResponseEntity<String> createFromActiveSubscriptions() {
        System.out.println("Creating jobs from active subscriptions function running...");
        boolean status = jobService.createJobsFromActiveSubscriptions();
        return ResponseEntity.ok("status: " + status);
    }

    @GetMapping("/{subscriptionId}/jobs")
    public ResponseEntity<List<Job>> getAllJobsBySubscriptionId(@PathVariable int subscriptionId) {
        List<Job> jobs = jobService.getJobsBySubscriptionId(subscriptionId);
        return ResponseEntity.ok(jobs);
    }

    @PutMapping("/{subscriptionId}/status")
    public ResponseEntity<Subscription> modifySubscriptionStatus(@PathVariable int subscriptionId, @RequestParam Subscription.subscriptionStatus status) {
        Subscription updatedSubscription = subscriptionService.modifySubscriptionStatus(subscriptionId, status);
        return ResponseEntity.ok(updatedSubscription);
    }

    @PostMapping
    public ResponseEntity<Subscription> createSubscription(@RequestBody Subscription subscription) {
        Subscription newSubscription = subscriptionService.createSubscription(subscription);
        return ResponseEntity.ok(newSubscription);
    }
}