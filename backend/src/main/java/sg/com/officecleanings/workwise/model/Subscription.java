package sg.com.officecleanings.workwise.model;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int subscriptionId;

    @ManyToOne
    @JoinColumn(name = "clientId", referencedColumnName = "clientId", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "propertyId", referencedColumnName = "propertyId", nullable = false)
    private Property property;

    @ManyToOne
    @JoinColumn(name = "packageId", referencedColumnName = "packageId", nullable = false)
    private SelectedPackage selectedPackage;

    private String packageType;
    private String jobDay;

    @Column(name = "job_starttime")
    private LocalTime jobStartTime;

    @Column(name = "job_endtime")
    private LocalTime jobEndTime;

    private String subscriptionStatus;

    public Subscription() {
    }

    public Subscription(Client client, Property property, SelectedPackage selectedPackage, String packageType, String jobDay, LocalTime jobStartTime, LocalTime jobEndTime, String subscriptionStatus) {
        this.client = client;
        this.property = property;
        this.selectedPackage = selectedPackage;
        this.packageType = packageType;
        this.jobDay = jobDay;
        this.jobStartTime = jobStartTime;
        this.jobEndTime = jobEndTime;
        this.subscriptionStatus = subscriptionStatus;
    }

    // Getters and Setters
    public int getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(int subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public SelectedPackage getSelectedPackage() {
        return selectedPackage;
    }

    public void setSelectedPackage(SelectedPackage selectedPackage) {
        this.selectedPackage = selectedPackage;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public String getJobDay() {
        return jobDay;
    }

    public void setJobDay(String jobDay) {
        this.jobDay = jobDay;
    }

    public LocalTime getJobStartTime() {
        return jobStartTime;
    }

    public void setJobStartTime(LocalTime jobStartTime) {
        this.jobStartTime = jobStartTime;
    }

    public LocalTime getJobEndTime() {
        return jobEndTime;
    }

    public void setJobEndTime(LocalTime jobEndTime) {
        this.jobEndTime = jobEndTime;
    }

    public String getSubscriptionStatus() {
        return subscriptionStatus;
    }

    public void setSubscriptionStatus(String subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }
}