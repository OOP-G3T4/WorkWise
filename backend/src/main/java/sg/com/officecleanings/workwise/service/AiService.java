package sg.com.officecleanings.workwise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.com.officecleanings.workwise.model.Job;
import sg.com.officecleanings.workwise.model.Employee;
import sg.com.officecleanings.workwise.model.EmployeeEvent;
import sg.com.officecleanings.workwise.model.SelectedPackage;
import sg.com.officecleanings.workwise.repository.JobRepository;
import sg.com.officecleanings.workwise.repository.EmployeeRepository;
import sg.com.officecleanings.workwise.repository.EmployeeEventRepository;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Collections;


@Service
public class AiService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeEventRepository employeeEventRepository;

    @Autowired
    private JobService jobService;

    @Autowired
    private EmployeeEventService employeeEventService;

    @Autowired
    private PackageService packageService;


    private final String context = "You are an assistant who helps in assigning the best and nearest employees for the jobs based on the following criteria. " +
            "Consider the following factors when making assignments: " +
            "1. The employee is free during the job's scheduled time and is not working on another job. " +
            "2. Ensure the employee is not on annual leave or medical leave. " +
            "3. Verify that the employee has not exceeded the maximum allowed of 44 working hours. Only when all peers have also reached the maximum hours will this constraint be removed." +
            "4. Ensure that the employee has a non-working day throughout the week. (A week is from Monday to Sunday)" +
            "5. The employee has a lunch (any 1h from 11am to 1pm) and dinner break (any 1h from 5pm to 7pm) before, between or after the job." +
            "6. The employee has enough time to travel to the next location (not including meal breaks) with a 30mins buffer for poor traffic. " +
            "7. The best employee will be the one that satisfy the above constraints with the shortest travelling time from their last known location. (can be from home or from the previous job address) " ;


    public StringBuilder createEmployeeAssignments() {
        // Retrieve all employees
        List<Employee> allEmployees = employeeRepository.findAll();

        // Retrieve today's date
//        LocalDate today = LocalDate.now();

        // Retrieve all pending jobs
        LocalDate date = LocalDate.parse("2024-11-10");
        List<Job> allPendingJobs = jobService.getPendingJobsInNextWeek(date);

        // retrieve all employee events in that week
        List<EmployeeEvent> affectedEmployeeEvents = employeeEventService.getEmployeeEventsByWeek(date);

        // retrieve all package details
        List<SelectedPackage> allPackages = packageService.getAllPackages();

        // Construct the prompt for OpenAI
        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append(context);
        String instructions = "The following tables include the information about the jobs, package details, employees and their events. " +
                "Based on the above criteria, assign the best employee(s) suited for the pending jobs. Check the job against the package details to find out the required manpower for the job. " +
                "Provide the best employee with the shortest travelling time from their last known location. " +
                "Also provide a reasoning on why is that the employee the best suited for the role, explain using the criteria." +
                "Do not return any other words and only the following format:\n" +
                "{\n" +
                "    \"assignments\": [\n" +
                "        {\n" +
                "            \"job_id\": [<Pending Job ID>],\n" +
                "            \"employees_id\": [<Employee1 ID>, ...],\n" +
                "            \"reasoning\": \"[<Your reasoning here>]\"\n" +
                "        }\n" +
                "        ...\n" +
                "    ]\n";
        promptBuilder.append(instructions);
        String pendingJobsString = "\nPending Jobs:\n ";
        promptBuilder.append(pendingJobsString);
        promptBuilder.append(allPendingJobs);
        String employeesDatabaseString = "\nEmployee Database:\n ";
        promptBuilder.append(employeesDatabaseString);
        promptBuilder.append(allEmployees);
        String packageDetailsDatabaseString = "\nPackage Details Database:\n ";
        promptBuilder.append(packageDetailsDatabaseString);
        promptBuilder.append(allPackages);
        String affectedEmployeeEventsString = "\nAffected Employee Events:\n ";
        promptBuilder.append(affectedEmployeeEventsString);
        promptBuilder.append(affectedEmployeeEvents);
        System.out.println(promptBuilder);

        return promptBuilder;
    }
}
