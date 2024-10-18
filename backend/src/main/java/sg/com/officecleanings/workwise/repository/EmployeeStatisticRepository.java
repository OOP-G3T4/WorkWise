package sg.com.officecleanings.workwise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.com.officecleanings.workwise.model.EmployeeStatistic;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeStatisticRepository extends JpaRepository<EmployeeStatistic, Integer> {
}
