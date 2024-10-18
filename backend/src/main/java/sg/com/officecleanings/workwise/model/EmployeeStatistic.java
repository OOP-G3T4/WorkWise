package sg.com.officecleanings.workwise.model;

import jakarta.persistence.*;
import java.util.Date;
import jakarta.validation.constraints.NotNull;


@Entity
public class EmployeeStatistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int statId;

    @ManyToOne
    @JoinColumn(name = "employeeId", referencedColumnName = "employeeId", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "eventId", referencedColumnName = "eventId", nullable = false)
    private EmployeeEvent event;

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;

    @NotNull
    private String periodType; // need to change to enum

    @NotNull
    private int jobsCompleted;

    @NotNull
    private int clientsServed;

    @NotNull
    private int newJobs;

    @NotNull
    private int cancelledJobs;

    @NotNull
    private int rescheduledJobs;

    @NotNull
    private int workingHours;

    @NotNull
    private int overtimeHours;

    @NotNull
    private int mcUsed;

    @NotNull
    private int alUsed;

    public EmployeeStatistic() {
    }

    public EmployeeStatistic(Employee employee, EmployeeEvent event, Date startDate, Date endDate, String periodType,
            int jobsCompleted, int clientsServed, int newJobs, int cancelledJobs, int rescheduledJobs, int workingHours,
            int overtimeHours, int mcUsed, int alUsed) {
        this.employee = employee;
        this.event = event;
        this.startDate = startDate;
        this.endDate = endDate;
        this.periodType = periodType;
        this.jobsCompleted = jobsCompleted;
        this.clientsServed = clientsServed;
        this.newJobs = newJobs;
        this.cancelledJobs = cancelledJobs;
        this.rescheduledJobs = rescheduledJobs;
        this.workingHours = workingHours;
        this.overtimeHours = overtimeHours;
        this.mcUsed = mcUsed;
        this.alUsed = alUsed;
    }

    public int getStatId() {
        return statId;
    }

    public void setStatId(int statId) {
        this.statId = statId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public EmployeeEvent getEvent() {
        return event;
    }

    public void setEvent(EmployeeEvent event) {
        this.event = event;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getPeriodType() {
        return periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public int getJobsCompleted() {
        return jobsCompleted;
    }

    public void setJobsCompleted(int jobsCompleted) {
        this.jobsCompleted = jobsCompleted;
    }

    public int getClientsServed() {
        return clientsServed;
    }

    public void setClientsServed(int clientsServed) {
        this.clientsServed = clientsServed;
    }

    public int getNewJobs() {
        return newJobs;
    }

    public void setNewJobs(int newJobs) {
        this.newJobs = newJobs;
    }

    public int getCancelledJobs() {
        return cancelledJobs;
    }

    public void setCancelledJobs(int cancelledJobs) {
        this.cancelledJobs = cancelledJobs;
    }

    public int getRescheduledJobs() {
        return rescheduledJobs;
    }

    public void setRescheduledJobs(int rescheduledJobs) {
        this.rescheduledJobs = rescheduledJobs;
    }

    public int getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(int workingHours) {
        this.workingHours = workingHours;
    }

    public int getOvertimeHours() {
        return overtimeHours;
    }

    public void setOvertimeHours(int overtimeHours) {
        this.overtimeHours = overtimeHours;
    }

    public int getMcUsed() {
        return mcUsed;
    }

    public void setMcUsed(int mcUsed) {
        this.mcUsed = mcUsed;
    }

    public int getAlUsed() {
        return alUsed;
    }

    public void setAlUsed(int alUsed) {
        this.alUsed = alUsed;
    }

    // Getters and Setters


    @Override
    public String toString() {
        return "EmployeeStatistic{" +
                "statId=" + statId +
                ", employee=" + employee +
                ", event=" + event +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", periodType='" + periodType + '\'' +
                ", jobsCompleted=" + jobsCompleted +
                ", clientsServed=" + clientsServed +
                ", newJobs=" + newJobs +
                ", cancelledJobs=" + cancelledJobs +
                ", rescheduledJobs=" + rescheduledJobs +
                ", workingHours=" + workingHours +
                ", overtimeHours=" + overtimeHours +
                ", mcUsed=" + mcUsed +
                ", alUsed=" + alUsed +
                '}';
    }
}
