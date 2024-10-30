package sg.com.officecleanings.workwise.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// For LocalDate fields
import java.time.LocalDate;

@Entity
public class Client {

    public enum Gender {
        MALE, FEMALE, OTHER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int clientId;

    @NotNull
    @Size(max = 120)
    private String name;

    @Email
    @NotNull
    @Size(max = 200)
    @Pattern(regexp = "^[\\w!#$%&'*+/=?`{|}~^.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$",
             message = "Invalid email format")
    private String email;

    @NotNull
    private String clientAddress;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull
    private int clientAge;

    @NotNull
    private LocalDate joinDate;

    @Size(max = 20)
    private String phoneNumber;

    public Client() {
    }

    public Client(String name, String phoneNumber, String email, String clientAddress, Gender gender, int clientAge, LocalDate joinDate) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.clientAddress = clientAddress;
        this.gender = gender;
        this.clientAge = clientAge;
        this.joinDate = joinDate;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public Gender getGender() {
        return this.gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getClientAge() {
        return clientAge;
    }

    public void setClientAge(int clientAge) {
        this.clientAge = clientAge;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    // Getters and Setters

    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + clientId +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", clientAddress='" + clientAddress + '\'' +
                ", gender='" + gender + '\'' +
                ", clientAge=" + clientAge +
                ", joinDate=" + joinDate +
                '}';
    }
}
