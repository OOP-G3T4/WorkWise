package sg.com.officecleanings.workwise.service;

import sg.com.officecleanings.workwise.repository.JobRepository;

public class SchedulerService {
    private final JobRepository jobRepository;

    public SchedulerService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public String createSchedule() {
        // Fetch data, prepare prompt, call OpenAI, process response
        
        return "Generated schedule data";
    }
}
