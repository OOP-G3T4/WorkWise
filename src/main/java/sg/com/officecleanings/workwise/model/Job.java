package sg.com.officecleanings.workwise.model;

import jakarta.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.Set;

@Entity
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int jobId;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "clientId", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "property_id", referencedColumnName = "propertyId", nullable = false)
    private Property property;

    @ManyToOne
    @JoinColumn(name = "package_id", referencedColumnName = "packageId", nullable = false)
    private SelectedPackage selectedPackage; //Note cannot use Package

    private Date date;
    private Time startTime;
    private String status; //need to change to enum
    private int actualDuration;

    @ManyToMany
    @JoinTable(name = "job_employee", joinColumns = @JoinColumn(name = "jobId"), inverseJoinColumns = @JoinColumn(name = "employeeId"))
    private Set<Employee> employees;

    public Job() {
    }

    public Job(Client client, Property property, SelectedPackage selectedPackage, Date date, Time startTime, String status, int actualDuration) {
        this.client = client;
        this.property = property;
        this.selectedPackage = selectedPackage;
        this.date = date;
        this.startTime = startTime;
        this.status = status;
        this.actualDuration = actualDuration;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

}
