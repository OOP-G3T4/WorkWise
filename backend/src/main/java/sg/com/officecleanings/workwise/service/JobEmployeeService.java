package sg.com.officecleanings.workwise.service;

import sg.com.officecleanings.workwise.model.Job;
import sg.com.officecleanings.workwise.model.JobEmployee;
import sg.com.officecleanings.workwise.model.Employee;
import sg.com.officecleanings.workwise.repository.EmployeeRepository;
import sg.com.officecleanings.workwise.repository.JobEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.com.officecleanings.workwise.repository.JobRepository;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@Service
public class JobEmployeeService {

    @Autowired
    private JobEmployeeRepository jobEmployeeRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<JobEmployee> getEmployeesByJob(Job job) {
        return jobEmployeeRepository.findByJob(job);
    }

    public List<JobEmployee> getJobsByEmployee(Employee employee) {
        return jobEmployeeRepository.findByEmployee(employee);
    }

    public void saveAssignments(Map<String, List<Map<String, Object>>> input) {
        List<Map<String, Object>> assignments = input.get("assignments");
        for (Map<String, Object> assignment : assignments) {
            int jobId = (int) assignment.get("job_id");
            String reasoning = (String) assignment.get("reasoning");
            Object employeesIdObj = assignment.get("employees_id");
            List<Integer> employeeIds = new ArrayList<>();
            if (employeesIdObj instanceof List<?>) {
                List<?> employeesIdList = (List<?>) employeesIdObj;
                for (Object id : employeesIdList) {
                    if (id instanceof Integer) {
                        employeeIds.add((Integer) id);
                    } else {
                        throw new IllegalArgumentException("Invalid employee ID type");
                    }
                }
            } else {
                throw new IllegalArgumentException("Invalid employees_id type");
            }

            Job job = jobRepository.findById(jobId).orElseThrow(() -> new IllegalArgumentException("Invalid job ID: " + jobId));

            for (Integer employeeId : employeeIds) {
                Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new IllegalArgumentException("Invalid employee ID: " + employeeId));
                JobEmployee jobEmployee = new JobEmployee(job, employee, reasoning);
                jobEmployeeRepository.save(jobEmployee);
            }
        }
    }

}

