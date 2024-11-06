package sg.com.officecleanings.workwise.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sg.com.officecleanings.workwise.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.sql.Date;
import java.util.List;
import java.time.LocalDate;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {

    @Query("SELECT MAX(j.date) FROM Job j WHERE j.subscription.client.clientId = :clientId AND j.subscription.property.propertyId = :propertyId")
    LocalDate findLatestJobDateByClientIdAndPropertyId(@Param("clientId") Integer clientId, @Param("propertyId") Integer propertyId);

    @Query("SELECT COUNT(j) FROM Job j WHERE j.subscription.subscriptionId = :subscriptionId AND MONTH(j.date) = :month AND YEAR(j.date) = :year")
    int countJobsForSubscriptionInMonth(@Param("subscriptionId") Integer subscriptionId, @Param("month") int month, @Param("year") int year);

    List<Job> findByDateOrderByStartTimeAsc(java.util.Date date);

    List<Job> findByDateBetweenOrderByDateAscStartTimeAsc(java.util.Date startDate, java.util.Date endDate);

    List<Job> findByDateBetweenAndStatusOrderByDateAscStartTimeAsc(java.util.Date startDate, java.util.Date endDate, Job.Status status);

    List<Job> findByDateOrderByStartTimeAsc(Date date);

    List<Job> findByDate(Date date);

    List<Job> findByDateAfter(Date date);

    List<Job> findByDateBetweenOrderByDateAscStartTimeAsc(Date startDate, Date endDate);

    // List<Job> findByPropertyPropertyId(int propertyId);
    @Query("SELECT j FROM Job j WHERE j.subscription.property.propertyId = :propertyId")
    List<Job> findByPropertyId(@Param("propertyId") int propertyId);
    
    List<Job> findByStatus(Job.Status status);

    List<Job> findByDateBetweenAndStatusOrderByDateAscStartTimeAsc(Date startDate, Date endDate, Job.Status status);

    List<Job> findBySubscriptionSubscriptionId(int subscriptionId);
}
