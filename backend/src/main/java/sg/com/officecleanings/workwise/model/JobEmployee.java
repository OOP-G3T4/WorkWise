package sg.com.officecleanings.workwise.model;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;
import sg.com.officecleanings.workwise.model.id.JobEmployeeId;

@Entity
public class JobEmployee {

    @EmbeddedId
    private JobEmployeeId id;

    @ManyToOne
    @MapsId("jobId")
    @JoinColumn(name = "jobId", referencedColumnName = "jobId", nullable = false)
    private Job job;

    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(name = "employeeId", referencedColumnName = "employeeId", nullable = false)
    private Employee employee;

    @Length(max = 1000)
    private String reasoning;

    // Default constructor
    public JobEmployee() {
        this.reasoning = null; // Default value
    }

    // Constructor with Job, Employee, and reasoning
    public JobEmployee(Job job, Employee employee, String reasoning) {
        this.job = job;
        this.employee = employee;
        this.reasoning = reasoning;
        this.id = new JobEmployeeId(job.getJobId(), employee.getEmployeeId());
    }

    // Getters and setters
    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getReasoning() {
        return reasoning;
    }

    public void setReasoning(String reasoning) {
        this.reasoning = reasoning;
    }

    public void setJobId(int jobId) {
        if (this.id == null) {
            this.id = new JobEmployeeId();
        }
        this.id.setJobId(jobId);
    }

    public void setEmployeeId(int employeeId) {
        if (this.id == null) {
            this.id = new JobEmployeeId();
        }
        this.id.setEmployeeId(employeeId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof JobEmployee))
            return false;
        JobEmployee that = (JobEmployee) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}