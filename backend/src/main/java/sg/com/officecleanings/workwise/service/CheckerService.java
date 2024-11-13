package sg.com.officecleanings.workwise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import sg.com.officecleanings.workwise.dto.JobAssignmentDTO;
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
import java.util.Map;
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

    // Check if the employee has free time for that week
    public boolean isEmployeeFreeForWeek(Employee employee, LocalDate startDate, LocalDate endDate) {
        // Check if the employee is on leave for the entire week
        List<EmployeeLeave> leaves = employeeLeaveRepository.findByEmployee_EmployeeId(employee.getEmployeeId());
        for (EmployeeLeave leave : leaves) {
            if ((leave.getStartDate().isBefore(startDate) || leave.getStartDate().isEqual(startDate)) &&
                    (leave.getEndDate().isAfter(endDate) || leave.getEndDate().isEqual(endDate))) {
                // Employee is on leave for the entire week
                return false;
            }
        }

        // Calculate the total working hours for the employee in the given week
        int totalWorkingHours = calculateEmployeeWorkingHours(employee, startDate, endDate);

        // Check if the employee has at least 3 hours of free time in the week
        int maxWorkingHours = 44; // Assuming a maximum of 44 working hours per week
        int freeHours = maxWorkingHours - totalWorkingHours;

        return freeHours >= 3;
    }



    /* ==== AI Post Checkers ==== */


    public Map.Entry<Boolean, String> validateBatchJobAssignments(List<JobAssignmentDTO> jobAssignments) {
        for (JobAssignmentDTO assignment : jobAssignments) {
            Job job = getJobDetails(assignment.getJobId());
            if (job == null) {
                System.out.println("Job not found: " + assignment.getJobId());
                return (Map.entry(false, "Job not found: " + assignment.getJobId()));
            }

            for (int employeeId : assignment.getEmployeeIds()) {
                Employee employee = getEmployeeDetails(employeeId);
                if (employee == null) {
                    System.out.println("Employee not found: " + employeeId);
                    return (Map.entry(false, "Employee not found: " + employeeId));
                }
                if (!hasNonWorkingDay(employee, job, jobAssignments)) {
                    return (Map.entry(false, "Employee does not have a non-working day in the week with job " + job.getJobId() + " and employee " + employee.getEmployeeId()));
                }
                if (hasJobTimeClash(employee, job, jobAssignments)) {
                    return (Map.entry(false, "Job time clash detected with job " + job.getJobId() + " and employee " + employee.getEmployeeId()));
                }
                if (!hasProperMealBreak(employee, job, jobAssignments)) {
                    return (Map.entry(false, "Employee does not have proper meal breaks with job " + job.getJobId() + " and employee " + employee.getEmployeeId()));
                }
                if (hasInsufficientTravelTime(employee, job, jobAssignments)) {
                    return (Map.entry(false, "Employee does not have sufficient travel time with job " + job.getJobId() + " and employee " + employee.getEmployeeId()));
                }
                if (hasExceededWorkingHours(employee, job, jobAssignments)) {
                    return (Map.entry(false, "Employee has exceeded working hours with job " + job.getJobId() + " and employee " + employee.getEmployeeId()));
                }
            }
        }
        return (Map.entry(true, ""));
    }

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
        if (hasJobTimeClash(employee, job, null)) {
            System.out.println("Job time clash detected");
            return false;
        }

        // Check if employee has exceeded work hours
        if (hasExceededWorkingHours(employee, job, null)) {
            System.out.println("Employee has exceeded working hours");
            return false;
        }

        // Check if employee has a sufficient travel buffer
        if (hasInsufficientTravelTime(employee, job, null)) {
            System.out.println("Employee does not have sufficient travel time");
            return false;
        }

        // Check if employee has at least one non-working day in the week
        if (!hasNonWorkingDay(employee, job, null)) {
            System.out.println("Employee does not have a non-working day in the week");
            return false;
        }

        // Check for meal break time clashes
        if (!hasProperMealBreak(employee, job, null)) {
            System.out.println("Employee does not have proper meal breaks");
            return false;
        }
        return isValid;
    }

    // Check if employee has at least one non-working day in the week
    private boolean hasNonWorkingDay(Employee employee, Job job, List<JobAssignmentDTO> jobAssignments) {
        LocalDate startDate = job.getDate().with(DayOfWeek.MONDAY);
        LocalDate endDate = job.getDate().with(DayOfWeek.SUNDAY);
        List<LocalDate> workingDays = new ArrayList<>();
        workingDays.add(job.getDate());

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            if (!jobEmployeeRepository.findByEmployeeAndDate(employee.getEmployeeId(), date).isEmpty() && !workingDays.contains(date)) {
                System.out.println("Employee has a job on " + date);
                workingDays.add(date);
            }
        }

        if (jobAssignments != null) {
            for (JobAssignmentDTO assignment : jobAssignments) {
                if (assignment.getEmployeeIds().contains(employee.getEmployeeId())) {
                    Job newJob = getJobDetails(assignment.getJobId());
                    if (newJob != null && !workingDays.contains(newJob.getDate())) {
                        System.out.println("Employee has a job on " + newJob.getDate());
                        workingDays.add(newJob.getDate());
                    }
                }
            }
        }

        return workingDays.size() < 7;
    }


    // Check if employee has another job on the same day and the job timings clash
    private boolean hasJobTimeClash(Employee employee, Job job, List<JobAssignmentDTO> jobAssignments) {
        List<Job> jobs = jobEmployeeRepository.findByEmployeeAndDate(employee.getEmployeeId(), job.getDate());
        LocalTime jobStart = job.getStartTime().toLocalTime();
        LocalTime jobEnd = jobStart.plusHours(job.getActualDuration() == 0 ? job.getSubscription().getSelectedPackage().getHours(): job.getActualDuration());

        for (Job j : jobs) {
            LocalTime lastJobStart = j.getStartTime().toLocalTime();
            LocalTime lastJobEnd = lastJobStart.plusHours(j.getActualDuration());
            if (jobStart.isBefore(lastJobEnd) && jobEnd.isAfter(lastJobStart)) {
                System.out.println("Job time clash detected");
                System.out.println("Job" + j.getJobId() + " : " + jobStart + " - " + jobEnd);
                System.out.println("Job" + j.getJobId() + " : " + lastJobStart + " - " + lastJobEnd);

                return true;
            }
        }

        if (jobAssignments != null) {
            for (JobAssignmentDTO assignment : jobAssignments) {
                if (assignment.getEmployeeIds().contains(employee.getEmployeeId())) {
                    Job newJob = getJobDetails(assignment.getJobId());
                    // if employee has no jobs
                    if (newJob != null && newJob.getDate().equals(job.getDate())) {
                        LocalTime newJobStart = newJob.getStartTime().toLocalTime();
                        LocalTime newJobEnd = newJobStart.plusHours(newJob.getActualDuration() == 0 ? newJob.getSubscription().getSelectedPackage().getHours() : newJob.getActualDuration());
                        if (jobStart.isBefore(newJobEnd) && jobEnd.isAfter(newJobStart)) {
                            if (job.getJobId() != newJob.getJobId()) {
                                System.out.println("Job time clash detected");
                                System.out.println("Job 1: " + jobStart + " - " + jobEnd);
                                System.out.println("Job 2: " + newJobStart + " - " + newJobEnd);
                                return true;
                            }
                        }
                    }
                }
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

    // hasExceededWorkingHours method
    private boolean hasExceededWorkingHours(Employee employee, Job job, List<JobAssignmentDTO> jobAssignments) {
        int maxHours = 44; // Maximum working hours per week
        LocalDate startDate = job.getDate().with(DayOfWeek.MONDAY);
        LocalDate endDate = job.getDate().with(DayOfWeek.SUNDAY);
        // get all the jobs of the employee in the week
        int totalHours = calculateEmployeeWorkingHours(employee, startDate, endDate);

        if (jobAssignments != null) {
            for (JobAssignmentDTO assignment : jobAssignments) {
                if (assignment.getEmployeeIds().contains(employee.getEmployeeId())) {
                    Job newJob = getJobDetails(assignment.getJobId());
                    if (newJob != null && (newJob.getDate().isAfter(startDate) && newJob.getDate().isBefore(endDate))) {
                        totalHours += newJob.getActualDuration();
                    }
                }
            }
        }

        System.out.println("Total hours: " + totalHours);
        return totalHours + job.getActualDuration() > maxHours;
    }

    // hasSufficientTravelTime method
    private boolean hasInsufficientTravelTime(Employee employee, Job job, List<JobAssignmentDTO> jobAssignments) {
        // Get the last job the employee worked on the same day
        List<Job> jobsOnSameDay = jobEmployeeRepository.findByEmployeeAndDate(employee.getEmployeeId(), job.getDate());

        if (jobsOnSameDay.isEmpty()) {
            // If there are no jobs on the same day, assume sufficient travel time
            return false;
        }

        Job lastJob = jobsOnSameDay.get(jobsOnSameDay.size() - 1);
        LocalTime lastJobEnd = lastJob.getStartTime().toLocalTime().plusHours(lastJob.getActualDuration());
        LocalTime newJobStart = job.getStartTime().toLocalTime();

        // Calculate travel time in minutes
        int travelTime = calculateTravelTime(lastJob.getSubscription().getProperty().getPostalCode(), job.getSubscription().getProperty().getPostalCode());

        // Check if there is enough time to travel between jobs
        boolean hasSufficientTime = lastJobEnd.plusMinutes(travelTime).isBefore(newJobStart);

        if (jobAssignments != null) {
            for (JobAssignmentDTO assignment : jobAssignments) {
                if (assignment.getEmployeeIds().contains(employee.getEmployeeId())) {
                    Job newJob = getJobDetails(assignment.getJobId());
                    if (newJob != null && newJob.getDate().equals(job.getDate())) {
                        LocalTime newJobEnd = newJob.getStartTime().toLocalTime().plusHours(newJob.getActualDuration());
                        if (lastJobEnd.plusMinutes(travelTime).isAfter(newJob.getStartTime().toLocalTime()) ||
                                newJobEnd.plusMinutes(travelTime).isAfter(newJobStart)) {
                            return true;
                        }
                    }
                }
            }
        }

        return !hasSufficientTime;
    }

    private boolean hasProperMealBreak(Employee employee, Job job, List<JobAssignmentDTO> jobAssignments) {
        LocalDate jobDate = job.getDate();
        LocalTime jobStart = job.getStartTime().toLocalTime();
        LocalTime jobEnd = jobStart.plusHours(job.getActualDuration() == 0 ? job.getSubscription().getSelectedPackage().getHours() : job.getActualDuration());

        LocalTime lunchStart = LocalTime.of(11, 0);
        LocalTime lunchEnd = LocalTime.of(13, 0);
        LocalTime dinnerStart = LocalTime.of(17, 0);
        LocalTime dinnerEnd = LocalTime.of(19, 0);

        List<Job> jobs = jobEmployeeRepository.findByEmployeeAndDate(employee.getEmployeeId(), jobDate);
        jobs.add(job);

        if (jobAssignments != null) {
            for (JobAssignmentDTO assignment : jobAssignments) {
                if (assignment.getEmployeeIds().contains(employee.getEmployeeId())) {
                    Job newJob = getJobDetails(assignment.getJobId());
                    if (newJob != null && newJob.getDate().equals(jobDate)) {
                        jobs.add(newJob);
                    }
                }
            }
        }

        if (hasInterruptedBreak(jobs, lunchStart, lunchEnd)) {
            return false;
        }

        return !hasInterruptedBreak(jobs, dinnerStart, dinnerEnd);
    }

    private boolean hasInterruptedBreak(List<Job> jobs, LocalTime breakStart, LocalTime breakEnd) {
        for (LocalTime time = breakStart; time.plusHours(1).isBefore(breakEnd) || time.plusHours(1).equals(breakEnd); time = time.plusMinutes(1)) {
            boolean isUninterrupted = true;
            for (Job j : jobs) {
                LocalTime jobStart = j.getStartTime().toLocalTime();
                LocalTime jobEnd = jobStart.plusHours(j.getActualDuration() == 0 ? j.getSubscription().getSelectedPackage().getHours() : j.getActualDuration());
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