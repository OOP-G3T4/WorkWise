package sg.com.officecleanings.workwise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sg.com.officecleanings.workwise.model.AdminEmployee;
import sg.com.officecleanings.workwise.model.Admin;
import sg.com.officecleanings.workwise.model.Employee;
import sg.com.officecleanings.workwise.repository.AdminEmployeeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AdminEmployeeService {

    @Autowired
    private AdminEmployeeRepository adminEmployeeRepository;

    public List<Employee> findEmployeesByAdminId(int adminId) {
        List<AdminEmployee> adminEmployees = adminEmployeeRepository.findByAdminAdminId(adminId);
        return adminEmployees.stream()
                .map(AdminEmployee::getEmployee)
                .toList();
    }

    public Optional<Admin> findAdminByEmployeeId(int employeeId) {
        Optional<AdminEmployee> adminEmployee = adminEmployeeRepository.findByEmployeeEmployeeId(employeeId);
        return adminEmployee.map(AdminEmployee::getAdmin);
    }

    @Transactional
    public void deleteByEmployeeId(int employeeId) {
        adminEmployeeRepository.deleteByEmployeeEmployeeId(employeeId);
    }

    public void save(AdminEmployee adminEmployee) {
        adminEmployeeRepository.save(adminEmployee);
    }

}

// public void createAdminEmployee(int employeeId, int adminId) {
// AdminEmployee adminEmployee = new AdminEmployee(new Admin(adminId), new
// Employee(employeeId));
// adminEmployeeRepository.save(adminEmployee);
// }

// public void deleteAdminEmployeeByEmployeeId(int employeeId) {
// adminEmployeeRepository.deleteByEmployeeId(employeeId);
// }

// public List<AdminEmployee> getAllAdminEmployees() {
// return adminEmployeeRepository.findAll();
// }

// public List<AdminEmployee> getAdminEmployeesByEmployeeId(int employeeId) {
// return adminEmployeeRepository.findByEmployeeEmployeeId(employeeId);
// }

// public List<AdminEmployee> getAdminEmployeesByAdminId(int adminId) {
// return adminEmployeeRepository.findByAdminAdminId(adminId);
// }

// public AdminEmployee saveAdminEmployee(AdminEmployee adminEmployee) {
// return adminEmployeeRepository.save(adminEmployee);
// }

// public void deleteAdminEmployee(int id) {
// adminEmployeeRepository.deleteById(id);
// }
