package sg.com.officecleanings.workwise.controller;

import org.springframework.web.bind.annotation.*;
import sg.com.officecleanings.workwise.service.SchedulerService;

@RestController
@RequestMapping("/api/scheduler")
public class SchedulerController {
    private final SchedulerService schedulerService;

    public SchedulerController(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @GetMapping("/generate")
    public String generateSchedule() {
        return schedulerService.createSchedule();
    }
}
