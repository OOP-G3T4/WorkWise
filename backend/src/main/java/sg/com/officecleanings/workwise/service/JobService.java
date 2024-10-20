package sg.com.officecleanings.workwise.service;

import sg.com.officecleanings.workwise.model.Employee;
import sg.com.officecleanings.workwise.model.Job;
import sg.com.officecleanings.workwise.model.JobEmployee;
import sg.com.officecleanings.workwise.model.id.JobEmployeeId;
import sg.com.officecleanings.workwise.repository.JobRepository;
import sg.com.officecleanings.workwise.repository.JobEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.sql.Date;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;


@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobEmployeeRepository JobEmployeeRepository;

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public Optional<Job> getJobById(int id) {
        return jobRepository.findById(id);
    }

    public Job saveJob(Job job) {
        Job savedJob = jobRepository.save(job);
        savedJob.getEmployees().forEach(employee -> {
            JobEmployee jobEmployee = new JobEmployee(savedJob, employee, null);
            JobEmployeeRepository.save(jobEmployee);
        });

        return savedJob;
    }

    public void deleteJob(int id) {
        jobRepository.deleteById(id);
    }

    public List<Job> getJobsByEmployeeId(int employeeId) {
        List<JobEmployee> jobEmployees = JobEmployeeRepository.findByIdEmployeeId(employeeId);
        List<Job> jobs = new ArrayList<>();
        for (JobEmployee jobEmployee : jobEmployees) {
            jobs.add(jobEmployee.getJob());
        }
        return jobs;
    }

    public List<Employee> getEmployeesByJobId(int jobId) {
        List<JobEmployee> jobEmployees = JobEmployeeRepository.findByIdJobId(jobId); 
        List<Employee> employees = new ArrayList<>();
        
        for (JobEmployee jobEmployee : jobEmployees) {
            employees.add(jobEmployee.getEmployee());
        }
        
        return employees;
    }

    public List<Job> getJobsByDay(Date date) {
        return jobRepository.findByDateOrderByStartTimeAsc(date);
    }

    public List<Job> getJobsByWeek(LocalDate date) {
        LocalDate startOfWeek = date.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        LocalDate endOfWeek = date.with(TemporalAdjusters.nextOrSame(java.time.DayOfWeek.SUNDAY));

        Date startDate = Date.valueOf(startOfWeek);
        Date endDate = Date.valueOf(endOfWeek);

        return jobRepository.findByDateBetweenOrderByDateAscStartTimeAsc(startDate, endDate);
    }

    public List<Job> getJobsByMonth(LocalDate date) {
        LocalDate startOfMonth = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());

        Date startDate = Date.valueOf(startOfMonth);
        Date endDate = Date.valueOf(endOfMonth);
        System.out.println("Start Date: " + startDate);
        System.out.println("End Date: " + endDate);

        return jobRepository.findByDateBetweenOrderByDateAscStartTimeAsc(startDate, endDate);
    }

    public List<Job> getJobsByStatus(String status) {
        return jobRepository.findByStatus(status);
    }

    public List<Job> getPendingJobsInNextWeek(LocalDate date) {
        LocalDate startOfWeek = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = startOfWeek.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));

        Date startDate = Date.valueOf(startOfWeek);
        Date endDate = Date.valueOf(endOfWeek);

        List<Job> pendingJobs = jobRepository.findByDateBetweenAndStatusOrderByDateAscStartTimeAsc(startDate, endDate, "PENDING");

        return pendingJobs;
    }

}
