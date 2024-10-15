package sg.com.officecleanings.workwise.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.com.officecleanings.workwise.model.Employee;
import sg.com.officecleanings.workwise.model.Job;
import sg.com.officecleanings.workwise.model.JobEmployee;
import sg.com.officecleanings.workwise.model.id.JobEmployeeId;

@Repository
public interface JobEmployeeRepository extends JpaRepository<JobEmployee, JobEmployeeId> {
    List<JobEmployee> findByJob(Job job);
    List<JobEmployee> findByEmployee(Employee employee);
    List<JobEmployee> findByIdEmployeeId(int employeeId);
    List<JobEmployee> findByIdJobId(int jobId);

}

