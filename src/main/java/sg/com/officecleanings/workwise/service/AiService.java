package sg.com.officecleanings.workwise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.com.officecleanings.workwise.model.Job;
import sg.com.officecleanings.workwise.model.Employee;
import sg.com.officecleanings.workwise.model.EmployeeEvent;
import sg.com.officecleanings.workwise.repository.JobRepository;
import sg.com.officecleanings.workwise.repository.EmployeeRepository;
import sg.com.officecleanings.workwise.repository.EmployeeEventRepository;

import java.sql.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AiService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeEventRepository employeeEventRepository;

    private final String instructions = "Find the best and nearest employee for each job.";

    public String createComplexPrompt(List<Job> jobs, Date date) {
        // Retrieve jobs for the specified date
        List<Job> jobsForDate = jobRepository.findByDateOrderByStartTimeAsc(date);

        // Retrieve all employees
        List<Employee> allEmployees = employeeRepository.findAll();
        // Retrieve employee events for the specified date
        List<EmployeeEvent> employeeEvents = employeeEventRepository.findByEventDate(date);

        // Find busy employee IDs from assigned jobs
        Set<Integer> busyEmployeeIds = jobsForDate.stream()
                .flatMap(job -> job.getEmployees().stream()
                        .map(employee -> employee.getEmployeeId()))
                .collect(Collectors.toSet());

        // Add busy employee IDs from events
        busyEmployeeIds.addAll(employeeEvents.stream()
                .map(event -> event.getEmployee().getEmployeeId())
                .collect(Collectors.toSet()));

        // Find available employees
        List<Employee> availableEmployees = allEmployees.stream()
                .filter(employee -> !busyEmployeeIds.contains(employee.getEmployeeId()))
                .collect(Collectors.toList());

        // Prepare job details and available employees for the AI prompt
        String jobDetails = jobs.stream()
                .map(job -> String.format("Job ID: %d, Address: %s", job.getJobId(), job.getProperty().getAddress()))
                .collect(Collectors.joining(", "));

        String availableEmployeeDetails = availableEmployees.stream()
                .map(employee -> String.format("Employee ID: %d, Name: %s, Address: %s", employee.getEmployeeId(), employee.getName(), employee.getHomeAddress()))
                .collect(Collectors.joining(", "));

        // Construct the prompt for the GPT-4 API
        return String.format("Instructions: %s\nJobs: %s\nAvailable Employees: %s", instructions, jobDetails, availableEmployeeDetails);
    }
}
