package sg.com.officecleanings.workwise.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import sg.com.officecleanings.workwise.model.Client;
import sg.com.officecleanings.workwise.model.ClientProperty;
import sg.com.officecleanings.workwise.model.Property;
import sg.com.officecleanings.workwise.model.Job;
import sg.com.officecleanings.workwise.repository.ClientPropertyRepository;
import sg.com.officecleanings.workwise.repository.ClientRepository;
import sg.com.officecleanings.workwise.repository.PropertyRepository;
import sg.com.officecleanings.workwise.repository.JobRepository;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientPropertyRepository clientPropertyRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private JobRepository jobRepository;

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getClientById(int id) {
        return clientRepository.findById(id);
    }

    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }


}
