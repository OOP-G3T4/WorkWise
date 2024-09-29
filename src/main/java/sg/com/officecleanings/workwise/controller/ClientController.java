package sg.com.officecleanings.workwise.controller;

import sg.com.officecleanings.workwise.model.Client;
import sg.com.officecleanings.workwise.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable int id) {
        Optional<Client> client = clientService.getClientById(id);
        return client.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Client createClient(@RequestBody Client client) {
        return clientService.saveClient(client);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable int id, @RequestBody Client clientDetails) {
        Optional<Client> client = clientService.getClientById(id);
        if (client.isPresent()) {
            clientDetails.setClientId(id);
            return ResponseEntity.ok(clientService.saveClient(clientDetails));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable int id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
