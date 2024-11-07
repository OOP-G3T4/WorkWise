package sg.com.officecleanings.workwise.dto;

import java.util.List;

public class JobAssignmentDTO {
    private int jobId;
    private List<Integer> employeeIds;
    private String reasoning;

    // Constructors
    public JobAssignmentDTO() {
    }

    public JobAssignmentDTO(int jobId, List<Integer> employeeIds, String reasoning) {
        this.jobId = jobId;
        this.employeeIds = employeeIds;
        this.reasoning = reasoning;
    }

    // Getters and Setters
    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public List<Integer> getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(List<Integer> employeeIds) {
        this.employeeIds = employeeIds;
    }

    public String getReasoning() {
        return reasoning;
    }
}