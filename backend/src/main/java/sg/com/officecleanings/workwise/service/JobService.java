package sg.com.officecleanings.workwise.service;

import sg.com.officecleanings.workwise.model.Subscription;
import sg.com.officecleanings.workwise.model.Employee;
import sg.com.officecleanings.workwise.model.Job;
import sg.com.officecleanings.workwise.model.JobEmployee;
import sg.com.officecleanings.workwise.repository.JobRepository;
import sg.com.officecleanings.workwise.repository.JobEmployeeRepository;
import sg.com.officecleanings.workwise.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private JobEmployeeRepository jobEmployeeRepository;

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
            jobEmployeeRepository.save(jobEmployee);
        });
        return savedJob;
    }

    public void deleteJob(int id) {
        jobRepository.deleteById(id);
    }

    public List<Job> getJobsByEmployeeId(int employeeId) {
        List<JobEmployee> jobEmployees = jobEmployeeRepository.findByIdEmployeeId(employeeId);
        List<Job> jobs = new ArrayList<>();
        jobEmployees.forEach(jobEmployee -> jobs.add(jobEmployee.getJob()));
        return jobs;
    }

    public List<Employee> getEmployeesByJobId(int jobId) {
        List<JobEmployee> jobEmployees = jobEmployeeRepository.findByIdJobId(jobId);
        List<Employee> employees = new ArrayList<>();
        jobEmployees.forEach(jobEmployee -> employees.add(jobEmployee.getEmployee()));
        return employees;
    }

    public List<Job> getJobsByDay(LocalDate date) {
        return jobRepository.findByDateOrderByStartTimeAsc(date);
    }

    public List<Job> getJobsByWeek(LocalDate date) {
        LocalDate startOfWeek = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        return jobRepository.findByDateBetweenOrderByDateAscStartTimeAsc(startOfWeek, endOfWeek);
    }

    public List<Job> getJobsByMonth(LocalDate date) {
        LocalDate startOfMonth = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());

        return jobRepository.findByDateBetweenOrderByDateAscStartTimeAsc(startOfMonth, endOfMonth);
    }

    public List<Job> getJobsByStatus(Job.Status status) {
        return jobRepository.findByStatus(status);
    }

    public List<Job> getPendingJobsInNextWeek(LocalDate date) {
        LocalDate startOfWeek = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = startOfWeek.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));

        return jobRepository.findByDateBetweenAndStatusOrderByDateAscStartTimeAsc(startOfWeek, endOfWeek, Job.Status.PENDING);
    }

    public boolean createJobsFromActiveSubscriptions() {
        System.out.println("Creating jobs from active subscriptions method called.");
        LocalDate today = LocalDate.of(2024, 11, 17);

        LocalDate targetWeekStart = today.plusWeeks(4).with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)).plusDays(1);
        LocalDate targetWeekEnd = targetWeekStart.plusDays(6);

        List<Subscription> activeSubscriptions = subscriptionRepository.findBySubscriptionStatus(Subscription.subscriptionStatus.ACTIVE);

        for (Subscription subscription : activeSubscriptions) {
            if ("BI_WEEKLY".equals(subscription.getSelectedPackage().getType().toString())) {
                if (canScheduleBiWeeklyJob(subscription, targetWeekStart)) {
                    createAndSaveJob(subscription, targetWeekStart);
                }
            } else if ("WEEKLY".equals(subscription.getSelectedPackage().getType().toString())) {
                createAndSaveJob(subscription, targetWeekStart);
            }
        }
        return true;
    }

    private boolean canScheduleBiWeeklyJob(Subscription subscription, LocalDate targetWeekStart) {
        LocalDate lastJobDate = jobRepository.findLatestJobDateBySubscriptionId(subscription.getSubscriptionId());

        int jobsThisMonth = jobRepository.countJobsForSubscriptionInMonth(subscription.getSubscriptionId(), targetWeekStart.getMonthValue(), targetWeekStart.getYear());
        if (jobsThisMonth >= 2) {
            return false;
        }

        boolean isJobDateValid = lastJobDate == null || lastJobDate.plusDays(10).isBefore(targetWeekStart);
        return isJobDateValid;
    }

    private void createAndSaveJob(Subscription subscription, LocalDate targetWeekStart) {
        DayOfWeek jobDay = DayOfWeek.valueOf(subscription.getJobDay().toUpperCase());
        LocalDate jobDate = targetWeekStart.with(TemporalAdjusters.nextOrSame(jobDay));

        long durationInHours = subscription.getSelectedPackage().getHours();

        Job job = new Job(
                subscription,
                jobDate,
                java.sql.Time.valueOf(subscription.getJobStartTime()),
                Job.Status.PENDING,
                (int) durationInHours,
                false,
                null,
                false,
                null
        );

        jobRepository.save(job);
    }

    public List<Job> getJobsBySubscriptionId(int subscriptionId) {
        return jobRepository.findBySubscriptionSubscriptionId(subscriptionId);
    }
}
