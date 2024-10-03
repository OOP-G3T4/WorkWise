package sg.com.officecleanings.workwise.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sg.com.officecleanings.workwise.model.Job;
import sg.com.officecleanings.workwise.repository.JobRepository;

import java.util.List;

@Service
public class SchedulerService {
    private final JobRepository jobRepository;
    private final String openAiUrl = "https://api.openai.com/v1/engines/davinci/completions";

    @Value("${openai.api.key}")
    private String openAiApiKey;

    public SchedulerService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public String createSchedule() {
        // Fetch jobs and employee availability
        List<Job> jobs = jobRepository.fetchAllJobs();

        // Optionally, fetch employee availability and other relevant data
        // List<Employee> employees = jobRepository.fetchAllEmployees();

        // Prepare the prompt for OpenAI
        String prompt = preparePrompt(jobs);

        // Call OpenAI API and get the response
        String response = callOpenAiApi(prompt);

        return response; // Processed response from OpenAI
    }

    private String preparePrompt(List<Job> jobs) {
        // Construct a prompt based on the job details
        StringBuilder promptBuilder = new StringBuilder("Generate an ideal schedule based on the following jobs:\n");

        for (Job job : jobs) {
            promptBuilder.append("Job ID: ").append(job.getJobId())
                         .append(", Client ID: ").append(job.getClientId())
                         .append(", Property ID: ").append(job.getPropertyId())
                         .append(", Package: ").append(job.getPackageId())
                         .append(", Date: ").append(job.getDate())
                         .append(", Start Time: ").append(job.getStartTime())
                         .append(", Duration: ").append(job.getActualDuration())
                         .append("\n");
        }

        // Add any other relevant context, such as employee availability
        // promptBuilder.append("Available Employees: " + employeeDetails + "\n");

        promptBuilder.append("Please provide a schedule for the next 1 week, considering all the details above.");
        return promptBuilder.toString();
    }

    private String callOpenAiApi(String prompt) {
        RestTemplate restTemplate = new RestTemplate();
        String jsonResponse;

        // Construct request body
        String requestBody = String.format("{\"prompt\": \"%s\", \"max_tokens\": 200}", prompt);

        // Call OpenAI API
        try {
            jsonResponse = restTemplate.postForObject(openAiUrl, requestBody, String.class);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error calling OpenAI API: " + e.getMessage();
        }

        return jsonResponse; // Return the raw response for further processing
    }
}