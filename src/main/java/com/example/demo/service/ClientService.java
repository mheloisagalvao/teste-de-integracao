package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Client;
import com.example.demo.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public Client updateClient(Long id, Client updatedClient) {
        Optional<Client> existingClient = clientRepository.findById(id);

        if (existingClient.isPresent()) {
            Client client = existingClient.get();
            client.setName(updatedClient.getName());
            client.setEmail(updatedClient.getEmail());
            // Defina outros campos conforme necessário
            return clientRepository.save(client);
        } else {
            throw new RuntimeException("Client not found for id: " + id);
        }
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}
