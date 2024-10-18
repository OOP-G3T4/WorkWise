package sg.com.officecleanings.workwise.repository;

import sg.com.officecleanings.workwise.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.sql.Date;
import java.util.List;
import java.time.LocalDate;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
    List<Job> findByDateOrderByStartTimeAsc(Date date);

    List<Job> findByDate(Date date);

    List<Job> findByDateAfter(Date date);

    List<Job> findByDateBetweenOrderByDateAscStartTimeAsc(Date startDate, Date endDate);

    List<Job> findByPropertyPropertyId(int propertyId);
    
    List<Job> findByStatus(String status);

    List<Job> findByDateBetweenAndStatusOrderByDateAscStartTimeAsc(Date startDate, Date endDate, String status);

}
