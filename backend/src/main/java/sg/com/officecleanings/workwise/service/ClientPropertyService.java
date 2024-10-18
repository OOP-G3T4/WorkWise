package sg.com.officecleanings.workwise.service;

import sg.com.officecleanings.workwise.model.ClientProperty;
import sg.com.officecleanings.workwise.repository.ClientPropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.com.officecleanings.workwise.model.Property;
import sg.com.officecleanings.workwise.model.Client;

import java.util.List;

@Service
public class ClientPropertyService {

    @Autowired
    private ClientPropertyRepository clientPropertyRepository;

    public List<Property> getPropertiesByClientId(int clientId) {
        List<ClientProperty> clientProperties = clientPropertyRepository.findByClientClientId(clientId);
        return clientProperties.stream()
                .map(ClientProperty::getProperty)
                .toList();
    }

    public Client getClientByPropertyId(int propertyId) {
        ClientProperty clientProperties = clientPropertyRepository.findByPropertyPropertyId(propertyId);
        return clientProperties.getClient();
    }
}
