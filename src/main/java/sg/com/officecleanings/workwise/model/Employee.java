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
    public int EmployeeID;
    
    public String Name;
    public String HomeAddress;
    public int PhoneNumber;
    public String Email;
    public int ALBalance;
    public int MCBalance;

    // Constructors
    public Employee() {
        // Default constructor required by JPA
    }
    public Employee(int EmployeeID, String Name, String HomeAddress, int PhoneNumber, String Email, int ALBalance, int MCBalance) {
        this.EmployeeID = EmployeeID;
        this.Name = Name;
        this.HomeAddress = HomeAddress;
        this.PhoneNumber = PhoneNumber;
        this.Email = Email;
        this.ALBalance = ALBalance;
        this.MCBalance = MCBalance;
    }
}
