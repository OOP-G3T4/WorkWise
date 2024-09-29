package sg.com.officecleanings.workwise.model;

import jakarta.persistence.*;
import java.util.Date;
import jakarta.validation.constraints.NotNull;


import io.micrometer.common.lang.Nullable;

@Entity
public class EmployeeEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int eventId;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "employeeId", nullable = false)
    private Employee employee;

    @ManyToOne
    @Nullable
    @JoinColumn(name = "job_id", referencedColumnName = "jobId")
    private Job job;

    @NotNull
    private String eventType; // need to change to enum

    @NotNull
    private Date eventDate;

    @Nullable
    private Integer duration;

    @Nullable
    private Integer mcUsed;

    @Nullable
    private Integer alUsed;

    public EmployeeEvent() {
    }

    public EmployeeEvent(Employee employee, Job job, String eventType, Date eventDate, Integer duration, Integer mcUsed, Integer alUsed) {
        this.employee = employee;
        this.job = job;
        this.eventType = eventType;
        this.eventDate = eventDate;
        this.duration = duration;
        this.mcUsed = mcUsed;
        this.alUsed = alUsed;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getMcUsed() {
        return mcUsed;
    }

    public void setMcUsed(Integer mcUsed) {
        this.mcUsed = mcUsed;
    }

    public Integer getAlUsed() {
        return alUsed;
    }

    public void setAlUsed(Integer alUsed) {
        this.alUsed = alUsed;
    }

    
}
