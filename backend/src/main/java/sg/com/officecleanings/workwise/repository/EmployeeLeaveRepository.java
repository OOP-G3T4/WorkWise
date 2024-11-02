package sg.com.officecleanings.workwise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.com.officecleanings.workwise.model.EmployeeLeave;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeLeaveRepository extends JpaRepository<EmployeeLeave, Integer> {
    List<EmployeeLeave> findByEmployeeEmployeeId(int employeeId);
}

