package sg.com.officecleanings.workwise.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sg.com.officecleanings.workwise.model.Employee;
import sg.com.officecleanings.workwise.model.Job;
import sg.com.officecleanings.workwise.model.JobEmployee;
import sg.com.officecleanings.workwise.model.id.JobEmployeeId;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface JobEmployeeRepository extends JpaRepository<JobEmployee, JobEmployeeId> {
    List<JobEmployee> findByJob(Job job);
    List<JobEmployee> findByEmployee(Employee employee);
    List<JobEmployee> findByIdEmployeeId(int employeeId);
    List<JobEmployee> findByIdJobId(int jobId);

    // Find last job of an employee based on both job date and job start time
    // Limit 1 is used to get only the last job
    // Only employeeId is used as parameter because job date and job start time are already present in Job object
    @Query("SELECT j FROM JobEmployee je JOIN je.job j WHERE je.employee.employeeId = :employeeId ORDER BY j.date DESC, j.startTime DESC")
    List<Job> findLastJobForEmployee(@Param("employeeId") int employeeId, Pageable pageable);
    @Query("SELECT SUM(j.actualDuration) FROM JobEmployee je JOIN je.job j WHERE je.employee.employeeId = :employeeId AND j.date BETWEEN :startDate AND :endDate")
    Integer getWeeklyWorkedHours(@Param("employeeId") int employeeId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    //findByEmployeeAndDate
    @Query("SELECT j FROM JobEmployee je JOIN je.job j WHERE je.employee.employeeId = :employeeId AND j.date = :date")
    List<Job> findByEmployeeAndDate(@Param("employeeId") int employeeId, @Param("date") LocalDate date);
}

