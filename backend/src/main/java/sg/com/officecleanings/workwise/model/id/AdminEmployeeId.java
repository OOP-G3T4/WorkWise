package sg.com.officecleanings.workwise.model.id;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class AdminEmployeeId implements Serializable {
    private Integer adminId;
    private Integer employeeId;

    // Equals, hashCode, constructors

    public AdminEmployeeId() {
    }

    public AdminEmployeeId(Integer adminId, Integer employeeId) {
        this.adminId = adminId;
        this.employeeId = employeeId;
    }

    
}

