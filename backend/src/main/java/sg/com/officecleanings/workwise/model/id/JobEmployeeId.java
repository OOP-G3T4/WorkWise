// src/main/java/sg/com/officecleanings/workwise/model/id/JobEmployeeId.java
package sg.com.officecleanings.workwise.model.id;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class JobEmployeeId implements Serializable {

    private int jobId;
    private int employeeId;

    // Default constructor
    public JobEmployeeId() {}

    // Constructor with parameters
    public JobEmployeeId(int jobId, int employeeId) {
        this.jobId = jobId;
        this.employeeId = employeeId;
    }

    // Getters and setters
    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobEmployeeId that = (JobEmployeeId) o;
        return jobId == that.jobId && employeeId == that.employeeId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobId, employeeId);
    }
}