package sg.com.officecleanings.workwise.repository;

import sg.com.officecleanings.workwise.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
}
