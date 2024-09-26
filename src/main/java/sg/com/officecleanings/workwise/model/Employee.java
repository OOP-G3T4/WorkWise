package sg.com.officecleanings.workwise.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Employee {
    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int employeeId;
    
    public String name;
    public String homeAddress;
    public int phoneNumber;
    public String email;
    public int alBalance;
    public int mcBalance;

    // Constructors
    public Employee() {
        // Default constructor required by JPA
    }
    public Employee(int employeeId, String name, String homeAddress, int phoneNumber, String email, int alBalance, int mcBalance) {
        this.employeeId = employeeId;
        this.name = name;
        this.homeAddress = homeAddress;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.alBalance = alBalance;
        this.mcBalance = mcBalance;
    }
}
