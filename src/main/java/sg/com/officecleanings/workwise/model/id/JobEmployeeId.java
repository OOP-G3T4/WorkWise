package sg.com.officecleanings.workwise.model.id;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class JobEmployeeId implements Serializable {
    private Integer jobId;
    private Integer employeeId;

    // Equals, hashCode, constructors

    public JobEmployeeId() {
    }

    public JobEmployeeId(Integer jobId, Integer employeeId) {
        this.jobId = jobId;
        this.employeeId = employeeId;
    }

    
}

