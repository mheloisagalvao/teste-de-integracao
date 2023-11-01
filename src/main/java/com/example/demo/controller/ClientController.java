package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Client;
import com.example.demo.service.ClientService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/client")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/add")
    public Client createClient(@RequestBody Client client) {
        Client createdClient = clientService.createClient(client);
        return createdClient;
    }

    @GetMapping("/all")
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    public Client getClientById(@PathVariable Long id) {
        Optional<Client> client = clientService.getClientById(id);

        if (client.isPresent()) {
            return client.get();
        } else {
            throw new RuntimeException("Client not found for id: " + id);
        }
    }

    @PutMapping("/update/{id}")
    public Client updateClient(@PathVariable Long id, @RequestBody Client updatedClient) {
        return clientService.updateClient(id, updatedClient);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
    }
}
