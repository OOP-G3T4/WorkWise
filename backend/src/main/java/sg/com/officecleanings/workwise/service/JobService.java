package sg.com.officecleanings.workwise.service;

import sg.com.officecleanings.workwise.model.Employee;
import sg.com.officecleanings.workwise.model.Job;
import sg.com.officecleanings.workwise.model.JobEmployee;
import sg.com.officecleanings.workwise.model.id.JobEmployeeId;
import sg.com.officecleanings.workwise.repository.JobRepository;
import sg.com.officecleanings.workwise.repository.JobEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.com.officecleanings.workwise.model.Subscription;
import sg.com.officecleanings.workwise.repository.SubscriptionRepository;

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
    private SubscriptionRepository subscriptionRepository;

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

    public List<Job> getJobsByStatus(Job.Status status) {
        return jobRepository.findByStatus(status);
    }

    public List<Job> getPendingJobsInNextWeek(LocalDate date) {
        LocalDate startOfWeek = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = startOfWeek.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));

        Date startDate = Date.valueOf(startOfWeek);
        Date endDate = Date.valueOf(endOfWeek);

        List<Job> pendingJobs = jobRepository.findByDateBetweenAndStatusOrderByDateAscStartTimeAsc(startDate, endDate, Job.Status.PENDING);

        return pendingJobs;
    }

    public boolean createJobsFromActiveSubscriptions() {
        System.out.println("Creating jobs from active subscriptions method called.");
        // Calculate the date range for the 4th week in advance
//        LocalDate today = LocalDate.now();
        LocalDate today = LocalDate.of(2024, 11, 3);

        LocalDate targetWeekStart = today.plusWeeks(4).with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)).plusDays(1);
        System.out.println("Target Week Start: " + targetWeekStart);
        LocalDate targetWeekEnd = targetWeekStart.plusDays(6); // 6 days to complete the week
        System.out.println("Target Week End: " + targetWeekEnd);

        List<Subscription> activeSubscriptions = subscriptionRepository.findBySubscriptionStatus("ACTIVE");

        for (Subscription subscription : activeSubscriptions) {
            if (subscription.getSelectedPackage().getType().equals("BI_WEEKLY")) {
                System.out.println("------------- Bi-weekly job --------------");

                // Check if we can schedule a bi-weekly job
                if (canScheduleBiWeeklyJob(subscription, targetWeekStart)) {
                    createAndSaveJob(subscription, targetWeekStart); // Schedule job on specified job day
                }
            } else if (subscription.getSelectedPackage().getType().equals("WEEKLY")) {
                System.out.println("------------- Weekly job ---------------");
                // Schedule weekly jobs for the target week
                createAndSaveJob(subscription, targetWeekStart);
            }
        }
        return true;
    }

    // Check if a bi-weekly job can be scheduled for the given subscription
    private boolean canScheduleBiWeeklyJob(Subscription subscription, LocalDate targetWeekStart) {
        System.out.println("Running canScheduleBiWeeklyJob method.");
        LocalDate lastJobDate = jobRepository.findLatestJobDateByClientIdAndPropertyId(subscription.getClient().getClientId(), subscription.getProperty().getPropertyId());

        // Check if there are already 2 jobs in the target month for this subscription
        int jobsThisMonth = jobRepository.countJobsForSubscriptionInMonth(subscription.getSubscriptionId(), targetWeekStart.getMonthValue(), targetWeekStart.getYear());
        if (jobsThisMonth >= 2) {
            System.out.println("Returning false due to month limit.");
            return false; // No more jobs needed if we already have 2 in this month
        }

        // If there is a last job date, and it is within the past 10 days, return false
        if (lastJobDate != null && lastJobDate.plusDays(10).isAfter(targetWeekStart)) {
            System.out.println("Returning false due to having a job less than 2 weeks ago.");
            return false;

        }

        System.out.println("Returning true.");
        return true;
    }

    // Helper method to create and save a job on the subscription's scheduled day within the target week
    private void createAndSaveJob(Subscription subscription, LocalDate targetWeekStart) {
        System.out.println("Creating and saving job.");
        // Determine job date within the target week
        DayOfWeek jobDay = DayOfWeek.valueOf(subscription.getJobDay().toUpperCase());
        LocalDate jobDate = targetWeekStart.with(TemporalAdjusters.nextOrSame(jobDay));
        // Calculate the duration in hours
        long durationInHours = subscription.getSelectedPackage().getHours();
        // long durationInHours = java.time.Duration.between(subscription.getJobStartTime(), subscription.getJobEndTime()).toHours();

        // Create a new Job using the provided constructor
        Job job = new Job(
                subscription.getClient(),
                subscription.getProperty(),
                subscription.getSelectedPackage(),
                subscription,
                java.sql.Date.valueOf(jobDate), // Convert LocalDate to java.sql.Date
                java.sql.Time.valueOf(subscription.getJobStartTime()), // Convert LocalTime to java.sql.Time
                Job.Status.PENDING,
                (int) durationInHours,
                false,
                false
        );

        System.out.println("Saving job.");
        jobRepository.save(job);
    }

}
