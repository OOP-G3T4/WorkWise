package sg.com.officecleanings.workwise.repository;

import sg.com.officecleanings.workwise.model.ClientProperty;
import sg.com.officecleanings.workwise.model.id.ClientPropertyId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientPropertyRepository extends JpaRepository<ClientProperty, ClientPropertyId> {
    List<ClientProperty> findByClientClientId(int clientId);
    ClientProperty findByPropertyPropertyId(int propertyId);
    void deleteByClient_ClientId(int clientId);
}
