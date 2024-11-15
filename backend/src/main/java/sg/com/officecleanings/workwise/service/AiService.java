package sg.com.officecleanings.workwise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.com.officecleanings.workwise.model.*;
import sg.com.officecleanings.workwise.repository.EmployeeEventRepository;
import sg.com.officecleanings.workwise.repository.EmployeeRepository;
import sg.com.officecleanings.workwise.repository.JobRepository;

import java.time.LocalDate;
import java.util.List;


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

    private final String context = "You are an assistant who helps in assigning the best and nearest employees for the jobs based on the following criteria: " +
            "1. Ensure no time clash with another job by checking the date, start time, and duration. " +
            "2. Ensure the employee is not on annual leave or medical leave. " +
            "3. Verify that the employee has not exceeded the maximum allowed 44 working hours. This constraint is removed only when all peers have also reached the maximum hours. " +
            "4. Ensure the employee has a non-working day throughout the week (Monday to Sunday). " +
            "5. Ensure the employee has a lunch break (1 hour between 11am and 2pm) and a dinner break (1 hour between 5pm and 8pm) before, between, or after the job. " +
            "6. Ensure the employee has enough time to travel to the next location with a 30-minute buffer for poor traffic. " +
            "7. The best employee will be the one that satisfies the above constraints with the shortest traveling time from their last known location (home or previous job address).";

    @Autowired
    private PropertyService propertyService;

    public StringBuilder createEmployeeAssignments() {
        // Retrieve all employees
        List<Employee> allEmployees = employeeRepository.findAll();

        // Retrieve today's date
        LocalDate date = LocalDate.now();

        // Retrieve all pending jobs
        List<Job> allPendingJobs = jobService.getPendingJobsInNextWeek(date);

        // Retrieve all employee events in that week
        List<EmployeeEvent> affectedEmployeeEvents = employeeEventService.getEmployeeEventsByWeek(date);

        // Retrieve all package details
        List<SelectedPackage> allPackages = packageService.getAllPackages();

        // Retrieve all property details
        List<Property> allProperties = propertyService.getAllProperties();

        // Construct the prompt for OpenAI
        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append(context);
        String instructions = "The following tables include information about the jobs, employees, and their events. " +
                "Based on the above criteria, assign the best employee(s) suited for the pending jobs. " +
                "Check the job against the package details to find out the required manpower for the job. " +
                "Provide the best employee with the shortest traveling time from their last known location. " +
                "Also, provide a reasoning on why that employee is the best suited for the role, explaining using the criteria. " +
                "Employees can do more than one job. " +
                "Reasoning should be one word. " +
                "Determine the number of employees by looking at the pax. " +
                "If the job has a package that states pax 2, then assign 2 employees to the job. " +
                "Account for the travel time. " +
                "Duration is in hours. " +
                "Ensure that employees have NO time clash with another job by looking at the date, start time, and duration. " +
                "Ensure that all employees have a similar number of jobs. " +
                "Do not return any other words and only use the following format:\n" +
                "{\n" +
                "    \"assignments\": [\n" +
                "        {\n" +
                "            \"job_id\": <Pending Job ID>,\n" +
                "            \"employees_id\": [<Employee1 ID>, ...],\n" +
                "            \"reasoning\": \"<Your reasoning here>\"\n" +
                "        }\n" +
                "        ...\n" +
                "    ]\n" +
                "}";
        promptBuilder.append(instructions);
        String pendingJobsString = "\nPending Jobs:\n ";
        promptBuilder.append(pendingJobsString);

        promptBuilder.append(allPendingJobs);
        String employeesDatabaseString = "\nEmployee Database:\n ";
        promptBuilder.append(employeesDatabaseString);
        promptBuilder.append(allEmployees);
        String affectedEmployeeEventsString = "\nAffected Employee Events:\n ";
        promptBuilder.append(affectedEmployeeEventsString);
        promptBuilder.append(affectedEmployeeEvents);
        System.out.println(promptBuilder);
        System.out.println(promptBuilder);

        return promptBuilder;
    }
}
