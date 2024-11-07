package sg.com.officecleanings.workwise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sg.com.officecleanings.workwise.model.EmployeeLeave;

import java.util.List;

@Repository
public interface EmployeeLeaveRepository extends JpaRepository<EmployeeLeave, Integer> {

    List<EmployeeLeave> findByEmployee_EmployeeId(int employeeId);

    List<EmployeeLeave> findByEmployeeEmployeeId(int employeeId);
}

