package sg.com.officecleanings.workwise.dto;

public class EmployeeRequest {
    private String name;
    private String homeAddress;
    private String phoneNumber;
    private String shortBio;
    private String email;
    private Integer alBalance;
    private Integer mcBalance;
    private Integer adminId;
    
    public EmployeeRequest() {
    }

    public EmployeeRequest(String name, String homeAddress, String phoneNumber, String shortBio, String email, Integer alBalance, Integer mcBalance, Integer adminId) {
        this.name = name;
        this.homeAddress = homeAddress;
        this.phoneNumber = phoneNumber;
        this.shortBio = shortBio;
        this.email = email;
        this.alBalance = alBalance;
        this.mcBalance = mcBalance;
        this.adminId = adminId;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
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

    public String getShortBio() {
        return shortBio;
    }

    public String getEmail() {
        return email;
    }

    public Integer getAlBalance() {
        return alBalance;
    }

    public Integer getMcBalance() {
        return mcBalance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setShortBio(String shortBio) {
        this.shortBio = shortBio;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAlBalance(Integer alBalance) {
        this.alBalance = alBalance;
    }

    public void setMcBalance(Integer mcBalance) {
        this.mcBalance = mcBalance;
    }
    
}
