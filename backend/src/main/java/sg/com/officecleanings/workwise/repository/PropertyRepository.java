package sg.com.officecleanings.workwise.repository;

import sg.com.officecleanings.workwise.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {
}
