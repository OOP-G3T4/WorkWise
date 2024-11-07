package sg.com.officecleanings.workwise.model;

import jakarta.persistence.*;

import java.sql.Time;
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

    // @ManyToOne
    // @JoinColumn(name = "clientId", referencedColumnName = "clientId", nullable = false)
    // private Client client;

    // @ManyToOne
    // @JoinColumn(name = "propertyId", referencedColumnName = "propertyId", nullable = false)
    // private Property property;

    // @ManyToOne
    // @JoinColumn(name = "packageId", referencedColumnName = "packageId", nullable = false)
    // private SelectedPackage selectedPackage; // Note cannot use Package

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

    @ManyToMany
    @JoinTable(name = "jobEmployee", joinColumns = @JoinColumn(name = "jobId"), inverseJoinColumns = @JoinColumn(name = "employeeId"))
    private Set<Employee> employees;

    public Job() {
    }

    public Job(Subscription subscription, Date date, Time startTime,
               Status status, int actualDuration, Boolean arrivalProofUploaded, Boolean completionProofUploaded) {
        // this.client = client;
        // this.property = property;
        // this.selectedPackage = selectedPackage;
        this.subscription = subscription;
        this.date = date;
        this.startTime = startTime;
        this.status = status;
        this.actualDuration = actualDuration;
        this.arrivalProofUploaded = arrivalProofUploaded;
        this.completionProofUploaded = completionProofUploaded;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    // public Client getClient() {
    //     return client;
    // }

    // public void setClient(Client client) {
    //     this.client = client;
    // }

    // public Property getProperty() {
    //     return property;
    // }

    // public void setProperty(Property property) {
    //     this.property = property;
    // }

    // public SelectedPackage getSelectedPackage() {
    //     return selectedPackage;
    // }

    // public void setSelectedPackage(SelectedPackage selectedPackage) {
    //     this.selectedPackage = selectedPackage;
    // }

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

    @Override
    public String toString() {
        return "Job{" +
                "jobId=" + jobId +
                // ", client=" + client +
                // ", property=" + property +
                // ", selectedPackage=" + selectedPackage +
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

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }
}
