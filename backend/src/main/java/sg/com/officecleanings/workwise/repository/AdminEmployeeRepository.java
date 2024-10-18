package sg.com.officecleanings.workwise.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sg.com.officecleanings.workwise.model.AdminEmployee;
import sg.com.officecleanings.workwise.model.Employee;
import sg.com.officecleanings.workwise.model.Admin;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminEmployeeRepository extends JpaRepository<AdminEmployee, Integer> {
    List<AdminEmployee> findByAdminAdminId(int adminId); 
    Optional<AdminEmployee> findByEmployeeEmployeeId(int employeeId);
    void deleteByEmployeeEmployeeId(int employeeId);


}
