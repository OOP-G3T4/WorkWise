package sg.com.officecleanings.workwise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sg.com.officecleanings.workwise.model.EmployeeEvent;

import java.util.Date;
import java.util.List;

@Repository
public interface EmployeeEventRepository extends JpaRepository<EmployeeEvent, Integer> {

    List<EmployeeEvent> findByEmployeeId(int employeeId);

    List<EmployeeEvent> findByEventDate(Date date);

    List<EmployeeEvent> findByEventDateBetweenOrderByEventDateAsc(Date startDate, Date endDate);


}