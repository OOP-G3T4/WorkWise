package sg.com.officecleanings.workwise.repository;

import sg.com.officecleanings.workwise.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
}

