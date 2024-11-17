package sg.com.officecleanings.workwise.model;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
public class Subscription {

    public enum subscriptionStatus {
        ACTIVE, PAUSED, CANCELLED
    }

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

    private String jobDay;

    @Column(name = "job_starttime")
    private LocalTime jobStartTime;

    @Enumerated(EnumType.STRING)
    private subscriptionStatus subscriptionStatus;

    public Subscription() {
    }

    public Subscription(Client client, Property property, SelectedPackage selectedPackage, String jobDay, LocalTime jobStartTime, subscriptionStatus subscriptionStatus) {
        this.client = client;
        this.property = property;
        this.selectedPackage = selectedPackage;
        this.jobDay = jobDay;
        this.jobStartTime = jobStartTime;
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

    public subscriptionStatus getSubscriptionStatus() {
        return subscriptionStatus;
    }

    public void setSubscriptionStatus(subscriptionStatus subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }
}