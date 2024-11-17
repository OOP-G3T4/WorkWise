package sg.com.officecleanings.workwise.model;

import jakarta.persistence.*;

import java.sql.Time;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class Job {
    public enum Status {
        PENDING, SCHEDULED, CANCELLED, IN_PROGRESS, ACTION_REQUIRED, COMPLETED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int jobId;

    @ManyToOne
    @JoinColumn(name = "subscriptionId", referencedColumnName = "subscriptionId", nullable = false)
    private Subscription subscription;

    private LocalDate date;
    private Time startTime;

    @Enumerated(EnumType.STRING)
    private Status status;

    private int actualDuration;

    private Boolean arrivalProofUploaded = false;

    private Boolean completionProofUploaded = false;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] arrivalProofImg;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] completionProofImg;

    @ManyToMany
    @JoinTable(name = "jobEmployee", joinColumns = @JoinColumn(name = "jobId"), inverseJoinColumns = @JoinColumn(name = "employeeId"))
    private Set<Employee> employees;

    public Job() {
    }

    public Job(Subscription subscription, LocalDate date, Time startTime,
            Status status, int actualDuration, Boolean arrivalProofUploaded, byte[] arrivalProofImg,
            Boolean completionProofUploaded,
            byte[] completionProofImg) {
        this.subscription = subscription;
        this.date = date;
        this.startTime = startTime;
        this.status = status;
        this.actualDuration = actualDuration;
        this.arrivalProofUploaded = arrivalProofUploaded;
        this.arrivalProofImg = arrivalProofImg;
        this.completionProofUploaded = completionProofUploaded;
        this.completionProofImg = completionProofImg;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getActualDuration() {
        return actualDuration;
    }

    public void setActualDuration(int actualDuration) {
        this.actualDuration = actualDuration;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Boolean getArrivalProofUploaded() {
        return arrivalProofUploaded;
    }

    public void setArrivalProofUploaded(Boolean arrivalProofUploaded) {
        this.arrivalProofUploaded = arrivalProofUploaded;
    }

    public Boolean getCompletionProofUploaded() {
        return completionProofUploaded;
    }

    public void setCompletionProofUploaded(Boolean completionProofUploaded) {
        this.completionProofUploaded = completionProofUploaded;
    }

    public byte[] getArrivalProofImg() {
        return arrivalProofImg;
    }

    public void setArrivalProofImg(byte[] arrivalProofImg) {
        this.arrivalProofImg = arrivalProofImg;
    }

    public byte[] getCompletionProofImg() {
        return completionProofImg;
    }

    public void setCompletionProofImg(byte[] completionProofImg) {
        this.completionProofImg = completionProofImg;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    @Override
    public String toString() {
        return "Job{" +
                "jobId=" + jobId +
                ", subscription=" + subscription +
                ", date=" + date +
                ", startTime=" + startTime +
                ", status='" + status + '\'' +
                ", actualDuration=" + actualDuration +
                ", employees=" + employees +
                ", arrivalProofUploaded=" + arrivalProofUploaded +
                ", completionProofUploaded=" + completionProofUploaded +
                '}';
    }

}
