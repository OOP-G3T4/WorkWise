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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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
        System.out.println("Checking job assignment for employee: " + employee.getEmployeeId() + " and job: " + job.getJobId());
        boolean isValid = true;
        // Check if employee is on AL or MC
        if (isOnLeave(employee, job.getDate())) {
            System.out.println("Employee is on leave");
            return false;
        }

        // Check if employee has another job on the same day and the job timings clash
        if (hasJobTimeClash(employee, job)) {
            System.out.println("Job time clash detected");
            return false;
        }

        // Check if employee has exceeded work hours
        if (hasExceededWorkingHours(employee, job)) {
            System.out.println("Employee has exceeded working hours");
            return false;
        }

        // Check if employee has a sufficient travel buffer
        if (!hasSufficientTravelTime(employee, job)) {
            System.out.println("Employee does not have sufficient travel time");
            return false;
        }

        // Check if employee has at least one non-working day in the week
        if (!hasNonWorkingDay(employee, job)) {
            System.out.println("Employee does not have a non-working day in the week");
            return false;
        }

        // Check for meal break time clashes
        if (!hasProperMealBreak(employee, job)) {
            System.out.println("Employee does not have proper meal breaks");
            return false;
        }
        return isValid;
    }

    // Check if employee has at least one non-working day in the week
    private boolean hasNonWorkingDay(Employee employee, Job job) {
        LocalDate startDate = job.getDate().with(DayOfWeek.MONDAY);
        LocalDate endDate = job.getDate().with(DayOfWeek.SUNDAY);
        // list of days the employee is free, declare with the job date
        // the list should have the job date inside by default
        List<LocalDate> workingDays = new ArrayList<>();
        workingDays.add(job.getDate());
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            // populate the list with the working days of the employee
            // if the employee has a job on that date, add it to the list
            // only add the date if it is not already in the list
            if (!jobEmployeeRepository.findByEmployeeAndDate(employee.getEmployeeId(), date).isEmpty() && !workingDays.contains(date)) {
                workingDays.add(date);
            }
        }
        System.out.println("Working days: " + workingDays);
        // check if there is at least one non-working day in the week by lookin at the count of working days
        return workingDays.size() < 7;
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
            if (jobStart.isBefore(lastJobEnd) && jobEnd.isAfter(lastJobStart)) {
                System.out.println("Job time clash detected with job: " + j.getJobId());
                return true;
            }
        }
        return false;
    }

    private boolean isOnLeave(Employee employee, LocalDate jobDate) {
        List<EmployeeLeave> leaves = employeeLeaveRepository.findByEmployee_EmployeeId(employee.getEmployeeId());
        for (EmployeeLeave leave : leaves) {
            // Check if the job date falls within the leave period

            if ((jobDate.isAfter(leave.getStartDate()) && jobDate.isBefore(leave.getEndDate())) ||
                    jobDate.equals(leave.getStartDate()) || jobDate.equals(leave.getEndDate())) {
                System.out.println("Employee is on leave on " + jobDate);
                return true; // Employee is on leave
            }
        }
        return false; // Employee is not on leave
    }

    private boolean hasExceededWorkingHours(Employee employee, Job job) {
        int maxHours = 44; // Maximum working hours per week
        LocalDate startDate = job.getDate().with(DayOfWeek.MONDAY);
        LocalDate endDate = job.getDate().with(DayOfWeek.SUNDAY);
        // get all the jobs of the employee in the week
        int totalHours = calculateEmployeeWorkingHours(employee, startDate, endDate);
        System.out.println("Total hours: " + totalHours);
        return totalHours + job.getActualDuration() > maxHours;
    }

    private boolean hasSufficientTravelTime(Employee employee, Job job) {
        Optional<Job> lastJobOptional = findLastJobForEmployee(employee);
        if (lastJobOptional.isEmpty()) {
            // If there is no last job, assume sufficient travel time
            return true;
        }

        Job lastJob = lastJobOptional.get();
        LocalTime lastJobEnd = lastJob.getStartTime().toLocalTime().plusHours(lastJob.getActualDuration());
        LocalTime newJobStart = job.getStartTime().toLocalTime();

        // Calculate travel time in minutes
        int travelTime = calculateTravelTime(lastJob.getProperty().getPostalCode(), job.getProperty().getPostalCode());

        // Check if there is enough time to travel between jobs
        return lastJobEnd.plusMinutes(travelTime).isBefore(newJobStart);
    }

    private boolean hasProperMealBreak(Employee employee, Job job) {
        LocalDate jobDate = job.getDate();
        LocalTime jobStart = job.getStartTime().toLocalTime();
        LocalTime jobEnd = jobStart.plusHours(job.getActualDuration() == 0 ? job.getSelectedPackage().getHours() : job.getActualDuration());

        // Define lunch and dinner break periods
        LocalTime lunchStart = LocalTime.of(11, 0);
        LocalTime lunchEnd = LocalTime.of(13, 0);
        LocalTime dinnerStart = LocalTime.of(17, 0);
        LocalTime dinnerEnd = LocalTime.of(19, 0);

        // Get all jobs for the employee on the same day
        List<Job> jobs = jobEmployeeRepository.findByEmployeeAndDate(employee.getEmployeeId(), jobDate);

        // Add the new job to the list of jobs
        jobs.add(job);

        // Check for lunch break
        if (hasInterruptedBreak(jobs, lunchStart, lunchEnd)) {
            System.out.println("Interrupted lunch break");
            return false;
        }

        // Check for dinner break
        if (hasInterruptedBreak(jobs, dinnerStart, dinnerEnd)) {
            System.out.println("Interrupted dinner break");
            return false;
        }

        System.out.println("Proper meal breaks");

        return true;
    }

    private boolean hasInterruptedBreak(List<Job> jobs, LocalTime breakStart, LocalTime breakEnd) {
        for (LocalTime time = breakStart; time.plusHours(1).isBefore(breakEnd) || time.plusHours(1).equals(breakEnd); time = time.plusMinutes(1)) {
            boolean isUninterrupted = true;
            for (Job j : jobs) {
                LocalTime jobStart = j.getStartTime().toLocalTime();
                LocalTime jobEnd = jobStart.plusHours(j.getActualDuration() == 0 ? j.getSelectedPackage().getHours() : j.getActualDuration());
                if (time.isBefore(jobEnd) && time.plusHours(1).isAfter(jobStart)) {
                    isUninterrupted = false;
                    break;
                }
            }
            if (isUninterrupted) {
                return false;
            }
        }
        return true;
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

    public int calculateEmployeeWorkingHours(Employee employee, LocalDate startDate, LocalDate endDate) {
        // Call the repository to calculate total working hours in the given date range
        return jobEmployeeRepository.getWeeklyWorkedHours(employee.getEmployeeId(), startDate, endDate);
    }
}