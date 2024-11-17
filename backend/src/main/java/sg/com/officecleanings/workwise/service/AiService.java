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

    private final String context = "You are an assistant who helps in assigning the best and nearest employees for the jobs based on the following criteria. " +
            "Account ALL employees, and Consider the following factors when making assignments: " +
            "1. Employee have no time clash with another job by looking at the date, start time and duration" +
            "2. Ensure the employee is not on annual leave or medical leave. " +
            "3. Verify that the employee has not exceeded the maximum allowed of 44 working hours. Only when all peers have also reached the maximum hours will this constraint be removed." +
            "4. Ensure that the employee has a non-working day throughout the week. (A week is from Monday to Sunday)" +
            "5. The employee has a lunch (any 1h from 11am to 2pm) and dinner break (any 1h from 5pm to 8pm) before, between or after the job." +
            "6. The employee has enough time to travel to the next location (not including meal breaks) with a 30mins buffer for poor traffic. " +
            "7. The best employee will be the one that satisfy the above constraints with the shortest travelling time from their last known location. (can be from home or from the previous job address) " ;
    @Autowired
    private PropertyService propertyService;

    public StringBuilder createEmployeeAssignments() {
        // Retrieve all employees
        List<Employee> allEmployees = employeeRepository.findAll();

        // Retrieve all pending jobs
        LocalDate date = LocalDate.now();
        List<Job> allPendingJobs = jobService.getPendingJobsInNextWeek(date);

        // retrieve all employee events in that week
        List<EmployeeEvent> affectedEmployeeEvents = employeeEventService.getEmployeeEventsByWeek(date);

        // retrieve all package details
        List<SelectedPackage> allPackages = packageService.getAllPackages();

        // retrieve all property details
        List<Property> allProperties = propertyService.getAllProperties();

        // Construct the prompt for OpenAI
        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append(context);
        String instructions = "The following tables include the information about the jobs, employees and their events. " +
                "Based on the above criteria, assign the best employee(s) suited for the pending jobs. Check the job against the package details to find out the required manpower for the job. " +
                "Provide the best employee with the shortest travelling time from their last known location. " +
                "Also provide a reasoning on why is that the employee the best suited for the role, explain using the criteria." +
                "Employee can do more than one job " +
                "Reasoning should be 1 word" +
                "Determine the amount of employees by looking at the pax" +
                "If the job has a package that states pax 2, then assign 2 employees to the job" +
                "Account for the travel time" +
                "Duration is in hours" +
                "Ensure that all employees have a similar number of jobs" +
                "Do not return any other words and only the following format:\n" +
                "{\n" +
                "    \"assignments\": [\n" +
                "        {\n" +
                "            \"job_id\": <Pending Job ID>,\n" +
                "            \"employees_id\": [<Employee1 ID>, ...],\n" +
                "            \"reasoning\": \"<Your reasoning here>\"\n" +
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
        String affectedEmployeeEventsString = "\nAffected Employee Events:\n ";
        promptBuilder.append(affectedEmployeeEventsString);
        promptBuilder.append(affectedEmployeeEvents);
        System.out.println(promptBuilder);
        System.out.println(promptBuilder);

        return promptBuilder;
    }
}
