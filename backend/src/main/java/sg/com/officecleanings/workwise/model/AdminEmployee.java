package sg.com.officecleanings.workwise.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import sg.com.officecleanings.workwise.model.id.AdminEmployeeId;

@Entity
public class AdminEmployee {

    @EmbeddedId
    private AdminEmployeeId id;

    @ManyToOne
    @MapsId("adminId")
    @JoinColumn(name = "adminId", referencedColumnName = "adminId", nullable = false)
    private Admin admin;

    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(name = "employeeId", referencedColumnName = "employeeId", nullable = false)
    private Employee employee;

    public AdminEmployee() {
    }

    public AdminEmployee(Admin admin, Employee employee) {
        this.admin = admin;
        this.employee = employee;
        this.id = new AdminEmployeeId(admin.getAdminId(), employee.getEmployeeId());
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof AdminEmployee))
            return false;
        AdminEmployee that = (AdminEmployee) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
