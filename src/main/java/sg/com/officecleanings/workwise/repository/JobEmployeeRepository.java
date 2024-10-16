package sg.com.officecleanings.workwise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sg.com.officecleanings.workwise.model.Employee;
import sg.com.officecleanings.workwise.model.Job;
import sg.com.officecleanings.workwise.model.JobEmployee;
import sg.com.officecleanings.workwise.model.id.JobEmployeeId;

import java.util.Date;
import java.util.List;

@Repository
public interface JobEmployeeRepository extends JpaRepository<JobEmployee, JobEmployeeId> {
    List<JobEmployee> findByJob(Job job);
    List<JobEmployee> findByEmployee(Employee employee);
    List<JobEmployee> findByIdEmployeeId(int employeeId);
    List<JobEmployee> findByIdJobId(int jobId);

    @Query("SELECT j FROM Job j JOIN JobEmployee je ON j.jobId = je.job.jobId WHERE je.employee.employeeId = :employeeId ORDER BY j.date DESC, j.startTime DESC")
    Job findLastJobForEmployee(@Param("employeeId") int employeeId);

    @Query("SELECT SUM(j.actualDuration) FROM JobEmployee je JOIN je.job j WHERE je.employee.employeeId = :employeeId AND j.date BETWEEN :startDate AND :endDate")
    Integer getWeeklyWorkedHours(@Param("employeeId") int employeeId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

}

