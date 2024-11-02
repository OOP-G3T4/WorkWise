package sg.com.officecleanings.workwise.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
public class EmployeeLeave {
    public enum LeaveType {
        AL, MC
    }

    public enum LeaveStatus {
        PENDING, APPROVED, REJECTED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeLeaveId;

    @ManyToOne
    @JoinColumn(name = "employeeId", referencedColumnName = "employeeId", nullable = false)
    private Employee employee;

    @Enumerated(EnumType.STRING)
    private LeaveType leaveType;

    private LocalDateTime applicationDateTime;
    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private LeaveStatus status;

    private String comments;
    private Boolean mcProofUploaded;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] mcProofImg;  

    public EmployeeLeave() {
    }

    public EmployeeLeave(Employee employee, LeaveType leaveType, LocalDateTime applicationDateTime, LocalDate startDate, LocalDate endDate, LeaveStatus status, String comments, Boolean mcProofUploaded, byte[] mcProofImg) {
        this.employee = employee;
        this.leaveType = leaveType;
        this.applicationDateTime = applicationDateTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.comments = comments;
        this.mcProofUploaded = mcProofUploaded;
        this.mcProofImg = mcProofImg;
    }

    public int getEmployeeLeaveId() {
        return employeeLeaveId;
    }

    public void setEmployeeLeaveId(int employeeLeaveId) {
        this.employeeLeaveId = employeeLeaveId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public LocalDateTime getApplicationDateTime() {
        return applicationDateTime;
    }

    public void setApplicationDateTime(LocalDateTime applicationDateTime) {
        this.applicationDateTime = applicationDateTime;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LeaveStatus getStatus() {
        return status;
    }

    public void setStatus(LeaveStatus status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Boolean getMcProofUploaded() {
        return mcProofUploaded;
    }

    public void setMcProofUploaded(Boolean mcProofUploaded) {
        this.mcProofUploaded = mcProofUploaded;
    }

    public byte[] getMcProofImg() {
        return mcProofImg;
    }

    public void setMcProofImg(byte[] mcProofImg) {
        this.mcProofImg = mcProofImg;
    }

    
}
