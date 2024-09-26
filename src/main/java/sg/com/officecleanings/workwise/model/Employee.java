package sg.com.officecleanings.workwise.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeId;

    @NotNull
    @Size(max = 120)
    private String name;

    @NotNull
    @Size(max = 200)
    private String homeAddress;

    private String phoneNumber;

    @Email
    @NotNull
    @Size(max = 200)
    private String email;

    @Min(0)
    private int alBalance = 0;

    @Min(0)
    private int mcBalance = 0;

    public Employee() {
    }

    public Employee(String name, String homeAddress, String phoneNumber, String email, int alBalance, int mcBalance) {
        this.name = name;
        this.homeAddress = homeAddress;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.alBalance = alBalance;
        this.mcBalance = mcBalance;
    }

    // Getters and Setters

    public int getId() {
        return employeeId;
    }

    public void setId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public int getAlBalance() {
        return alBalance;
    }

    public void setAlBalance(int alBalance) {
        if (alBalance >= 0) this.alBalance = alBalance;
    }

    public int getMcBalance() {
        return mcBalance;
    }

    public void setMcBalance(int mcBalance) {
        if (mcBalance >= 0) this.mcBalance = mcBalance;
    }
}