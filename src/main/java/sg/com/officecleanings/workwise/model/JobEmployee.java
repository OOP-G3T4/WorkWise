package sg.com.officecleanings.workwise.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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

    // Getters, setters, constructors
    public JobEmployee() {
    }

    public JobEmployee(Job job, Employee employee) {
        this.job = job;
        this.employee = employee;
        this.id = new JobEmployeeId(job.getJobId(), employee.getEmployeeId());
    }

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
