package com.example.demo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.example.demo.model.Client;
import com.example.demo.repository.ClientRepository;
import com.example.demo.service.ClientService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class ClientTest {

    @Mock
    private ClientRepository clientRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetClientName() {
        
        Client client = new Client();
        client.setName("Alice");

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        ClientService clientService = new ClientService(clientRepository);

        Optional<Client> retrievedClient = clientService.getClientById(1L);

        assertEquals("Alice", retrievedClient.get().getName());
    }

    @Test
    public void testCreateClient() {
        
        Client client = new Client();
        client.setName("Bob");

        when(clientRepository.save(Mockito.any(Client.class))).thenReturn(client);

        ClientService clientService = new ClientService(clientRepository);

        Client createdClient = clientService.createClient(client);

        assertEquals("Bob", createdClient.getName());
    }

    @Test
    public void testUpdateClient() {
        
        Client client = new Client();
        client.setId(1L);
        client.setName("Charlie");

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(clientRepository.save(Mockito.any(Client.class))).thenReturn(client);

        ClientService clientService = new ClientService(clientRepository);

        Client updatedClient = new Client();
        updatedClient.setId(1L);
        updatedClient.setName("David");

        Client result = clientService.updateClient(1L, updatedClient);

        assertEquals("David", result.getName());
    }

    @Test
    public void testDeleteClient() {

        when(clientRepository.save(Mockito.any(Client.class))).thenReturn(new Client());

        ClientService clientService = new ClientService(clientRepository);

        Client client = new Client();
        client.setName("Eve");

        Client createdClient = clientService.createClient(client);

        clientService.deleteClient(createdClient.getId());

        when(clientRepository.findById(createdClient.getId())).thenReturn(Optional.empty());
        Optional<Client> deletedClient = clientService.getClientById(createdClient.getId());
        assertEquals(false, deletedClient.isPresent());
    }

    @Test
    public void testGetAllClients() {

        List<Client> clientList = new ArrayList<>();
        Client client1 = new Client();
        client1.setId(1L);
        client1.setName("Alice");
        client1.setEmail("alice@example.com");
        clientList.add(client1);

        Client client2 = new Client();
        client2.setId(2L);
        client2.setName("Bob");
        client2.setEmail("bob@example.com");
        clientList.add(client2);

        when(clientRepository.findAll()).thenReturn(clientList);

        ClientService clientService = new ClientService(clientRepository);

        List<Client> retrievedClients = clientService.getAllClients();

        assertEquals(2, retrievedClients.size());
        assertEquals("Alice", retrievedClients.get(0).getName());
        assertEquals("Bob", retrievedClients.get(1).getName());
    }
}
