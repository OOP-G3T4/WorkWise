package sg.com.officecleanings.workwise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import sg.com.officecleanings.workwise.model.Employee;
import sg.com.officecleanings.workwise.model.EmployeeLeave;
import sg.com.officecleanings.workwise.model.Job;
import sg.com.officecleanings.workwise.repository.EmployeeLeaveRepository;
import sg.com.officecleanings.workwise.repository.EmployeeRepository;
import sg.com.officecleanings.workwise.repository.JobEmployeeRepository;
import sg.com.officecleanings.workwise.repository.JobRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CheckerService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeLeaveRepository employeeLeaveRepository;
    @Autowired
    private JobEmployeeRepository jobEmployeeRepository;

    /* ==== SubscriptionEligibilityChecker ==== */

    /* ==== JobEditAvailabilityChecker ==== */
    // Get the job details
    public Job getJobDetails(int jobId) {
        return jobRepository.findById(jobId).orElse(null);
    }


    // Get the employee details
    public Employee getEmployeeDetails(int employeeId) {
        return employeeRepository.findById(employeeId).orElse(null);
    }

    public boolean isEmployeeAvailableForJob(int employeeId, int jobId) {
        Job job = getJobDetails(jobId);
        Employee employee = getEmployeeDetails(employeeId);
        return isJobAssignmentValid(employee, job);
    }

    // Get a list of employees who are available for the job
    public List<Employee> getAvailableEmployeesForJob(int jobId) {
        Job job = getJobDetails(jobId);
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .filter(employee -> isJobAssignmentValid(employee, job))
                .toList();
    }


    /* ==== AI Pre Checkers ==== */


    /* ==== AI Post Checkers ==== */
    // Check if the employee can be assigned to a job
    public boolean isJobAssignmentValid(Employee employee, Job job) {
        // Check if employee is on AL or MC
        if (isOnLeave(employee, job.getDate())) {
            return false;
        }

        // Check if employee has another job on the same day and the job timings clash
        if (hasJobTimeClash(employee, job)) {
            return false;
        }

        // Check if employee has exceeded work hours
        if (hasExceededWorkingHours(employee, job)) {
            return false;
        }

        // Check if employee has a sufficient travel buffer
        if (!hasSufficientTravelTime(employee, job)) {
            return false;
        }

        // Check for meal break time clashes
        return hasProperMealBreak(employee, job);
    }

    // Check if employee has another job on the same day and the job timings clash
    private boolean hasJobTimeClash(Employee employee, Job job) {
        System.out.println("Checking for job time clash");
        List<Job> jobs = jobEmployeeRepository.findByEmployeeAndDate(employee.getEmployeeId(), job.getDate());
        LocalTime jobStart = job.getStartTime().toLocalTime();
        LocalTime jobEnd = jobStart.plusHours(job.getActualDuration() == 0 ? job.getSelectedPackage().getHours() : job.getActualDuration());

        for (Job j : jobs) {
            LocalTime lastJobStart = j.getStartTime().toLocalTime();
            LocalTime lastJobEnd = lastJobStart.plusHours(j.getActualDuration());
            System.out.println("Job start: " + jobStart + " Job end: " + jobEnd);
            if (jobStart.isBefore(lastJobEnd) && jobEnd.isAfter(lastJobStart)) {
                return true;
            }
        }
        return false;
    }

    private boolean isOnLeave(Employee employee, LocalDate jobDate) {
        List<EmployeeLeave> leaves = employeeLeaveRepository.findByEmployee_EmployeeId(employee.getEmployeeId());
        for (EmployeeLeave leave : leaves) {
            // Check if the job date falls within the leave period

            if (jobDate.isAfter(leave.getStartDate()) && jobDate.isBefore(leave.getEndDate())) {
                return true; // Employee is on leave
            }
        }
        return false; // Employee is not on leave
    }

    private boolean hasExceededWorkingHours(Employee employee, Job job) {
        // to be implemented
        return false;
    }

    private boolean hasSufficientTravelTime(Employee employee, Job job) {
        Optional<Job> lastJob = findLastJobForEmployee(employee); // Get the last job the employee worked on
        if (lastJob.isEmpty()) return true; // First job of the day

        Job lastJobEntity = lastJob.get();
        if (lastJobEntity.getProperty() == null || job.getProperty() == null) {
            return false; // If either property is null, travel time cannot be calculated
        }

        // Calculate travel time between last job location and current job location
        int travelTime = calculateTravelTime(lastJobEntity.getProperty().getAddress(), job.getProperty().getAddress());
        return travelTime <= 30; // Must be within 30 minutes
    }

    private boolean hasProperMealBreak(Employee employee, Job job) {
        LocalTime jobStart = job.getStartTime().toLocalTime();
        LocalTime jobEnd = jobStart.plusHours(job.getActualDuration());

        // Check if the job time overlaps with lunch (11am - 1pm) or dinner (5pm - 7pm)
        return !(jobStart.isAfter(LocalTime.of(11, 0)) && jobEnd.isBefore(LocalTime.of(13, 0)))
                || !(jobStart.isAfter(LocalTime.of(17, 0)) && jobEnd.isBefore(LocalTime.of(19, 0)));
    }

    // To be integrated with a distance API
    private int calculateTravelTime(String fromAddress, String toAddress) {
        // Call a distance API or use a map service to calculate travel time
        return 25; // Return in minutes
    }

    private Optional<Job> findLastJobForEmployee(Employee employee) {
        // Find the last job the employee worked on before this job
        return jobEmployeeRepository.findLastJobForEmployee(employee.getEmployeeId(), PageRequest.of(0, 1)).stream().findFirst();
    }

    public int calculateEmployeeWorkingHours(Employee employee, Date startDate, Date endDate) {
        // Call the repository to calculate total working hours in the given date range
        return jobEmployeeRepository.getWeeklyWorkedHours(employee.getEmployeeId(), startDate, endDate);
    }
}