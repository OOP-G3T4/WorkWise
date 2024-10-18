package sg.com.officecleanings.workwise.repository;

import sg.com.officecleanings.workwise.model.SelectedPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageRepository extends JpaRepository<SelectedPackage, String> {
}
