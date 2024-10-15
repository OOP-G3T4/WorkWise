package sg.com.officecleanings.workwise.service;

import sg.com.officecleanings.workwise.model.Job;
import sg.com.officecleanings.workwise.model.JobEmployee;
import sg.com.officecleanings.workwise.model.Employee;
import sg.com.officecleanings.workwise.repository.JobEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobEmployeeService {

    @Autowired
    private JobEmployeeRepository jobEmployeeRepository;

    public List<JobEmployee> getEmployeesByJob(Job job) {
        return jobEmployeeRepository.findByJob(job);
    }

    public List<JobEmployee> getJobsByEmployee(Employee employee) {
        return jobEmployeeRepository.findByEmployee(employee);
    }

}

