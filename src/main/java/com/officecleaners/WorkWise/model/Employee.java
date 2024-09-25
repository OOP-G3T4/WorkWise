package com.officecleaners.WorkWise.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    public Employee(int employeeID, String name, String homeAddress, int phoneNumber, String email, int ALBalance, int MCBalance) {
        this.EmployeeID = employeeID;
        this.Name = name;
        this.HomeAddress = homeAddress;
        this.PhoneNumber = phoneNumber;
        this.Email = email;
        this.ALBalance = ALBalance;
        this.MCBalance = MCBalance;
    }

    // Getters and Setters
    public int getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(int employeeID) {
        EmployeeID = employeeID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getHomeAddress() {
        return HomeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        HomeAddress = homeAddress;
    }

    public int getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getALBalance() {
        return ALBalance;
    }

    public void setALBalance(int ALBalance) {
        this.ALBalance = ALBalance;
    }

    public int getMCBalance() {
        return MCBalance;
    }

    public void setMCBalance(int MCBalance) {
        this.MCBalance = MCBalance;
    }
}
