package sg.com.officecleanings.workwise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.com.officecleanings.workwise.dto.JobAssignmentDTO;
import sg.com.officecleanings.workwise.model.Employee;
import sg.com.officecleanings.workwise.model.Job;
import sg.com.officecleanings.workwise.model.JobEmployee;
import sg.com.officecleanings.workwise.repository.EmployeeRepository;
import sg.com.officecleanings.workwise.repository.JobEmployeeRepository;
import sg.com.officecleanings.workwise.repository.JobRepository;

import java.util.List;

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

    public void saveAssignments(List<JobAssignmentDTO> assignments) {
        for (JobAssignmentDTO assignment : assignments) {
            int jobId = assignment.getJobId();
            String reasoning = assignment.getReasoning();
            List<Integer> employeeIds = assignment.getEmployeeIds();

            Job job = jobRepository.findById(jobId).orElseThrow(() -> new IllegalArgumentException("Invalid job ID: " + jobId));

            for (Integer employeeId : employeeIds) {
                Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new IllegalArgumentException("Invalid employee ID: " + employeeId));
                JobEmployee jobEmployee = new JobEmployee(job, employee, reasoning);
                jobEmployeeRepository.save(jobEmployee);
            }
        }
    }

}

