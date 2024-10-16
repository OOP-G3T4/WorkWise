package sg.com.officecleanings.workwise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.com.officecleanings.workwise.model.Employee;
import sg.com.officecleanings.workwise.model.EmployeeEvent;
import sg.com.officecleanings.workwise.model.Job;
import sg.com.officecleanings.workwise.repository.EmployeeEventRepository;
import sg.com.officecleanings.workwise.repository.EmployeeRepository;
import sg.com.officecleanings.workwise.repository.JobEmployeeRepository;
import sg.com.officecleanings.workwise.repository.JobRepository;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Service
public class CheckerService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeEventRepository employeeEventRepository;
    @Autowired
    private JobEmployeeRepository jobEmployeeRepository;

    // Check if the employee can be assigned to a job
    public boolean isJobAssignmentValid(Employee employee, Job job) {
        // Check if employee is on AL or MC
        if (isOnLeave(employee, job.getDate())) {
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

    private boolean isOnLeave(Employee employee, Date jobDate) {
        List<EmployeeEvent> events = employeeEventRepository.findByEmployeeId(employee.getEmployeeId());
        for (EmployeeEvent event : events) {
            // Check if event date matches the job date
            if (event.getEventDate().equals(jobDate)) {
                // Check if AL or MC leave is used, making sure to handle potential null values
                Integer alUsed = event.getAlUsed();
                Integer mcUsed = event.getMcUsed();
                if ((alUsed != null && alUsed > 0) || (mcUsed != null && mcUsed > 0)) {
                    return true; // Employee is on leave (AL or MC)
                }
            }
        }
        return false; // Employee is not on leave
    }

    private boolean hasExceededWorkingHours(Employee employee, Job job) {
        // to be implemented
        return false;
    }

    private boolean hasSufficientTravelTime(Employee employee, Job job) {
        Job lastJob = findLastJobForEmployee(employee); // Get the last job the employee worked on
        if (lastJob == null) return true; // First job of the day

        // Calculate travel time between last job location and current job location
        int travelTime = calculateTravelTime(lastJob.getProperty().getAddress(), job.getProperty().getAddress());
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

    private Job findLastJobForEmployee(Employee employee) {
        // Find the last job the employee worked on before this job
        return jobEmployeeRepository.findLastJobForEmployee(employee.getEmployeeId());
    }

    public int calculateEmployeeWorkingHours(Employee employee, Date startDate, Date endDate) {
        // Call the repository to calculate total working hours in the given date range
        return jobEmployeeRepository.getWeeklyWorkedHours(employee.getEmployeeId(), startDate, endDate);
    }
}