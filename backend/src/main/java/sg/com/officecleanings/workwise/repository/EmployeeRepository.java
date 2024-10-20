package sg.com.officecleanings.workwise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.com.officecleanings.workwise.model.Employee;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
