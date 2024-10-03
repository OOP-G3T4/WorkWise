package sg.com.officecleanings.workwise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.com.officecleanings.workwise.model.EmployeeEvent;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeEventRepository extends JpaRepository<EmployeeEvent, Integer> {
}