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
        // Crie um cliente de exemplo
        Client client = new Client();
        client.setName("Alice");

        // Use o Mockito para simular o comportamento do repositório
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        // Crie uma instância do serviço
        ClientService clientService = new ClientService(clientRepository);

        // Obtenha o cliente pelo ID (isso normalmente seria chamado pelo serviço)
        Optional<Client> retrievedClient = clientService.getClientById(1L);

        // Verifique se o nome do cliente corresponde ao esperado
        assertEquals("Alice", retrievedClient.get().getName());
    }

    @Test
    public void testCreateClient() {
        // Crie um cliente de exemplo
        Client client = new Client();
        client.setName("Bob");

        // Use o Mockito para simular o comportamento do repositório
        when(clientRepository.save(Mockito.any(Client.class))).thenReturn(client);

        // Crie uma instância do serviço
        ClientService clientService = new ClientService(clientRepository);

        // Crie o cliente
        Client createdClient = clientService.createClient(client);

        // Verifique se o cliente foi criado corretamente
        assertEquals("Bob", createdClient.getName());
    }

    @Test
    public void testUpdateClient() {
        // Crie um cliente de exemplo
        Client client = new Client();
        client.setId(1L);
        client.setName("Charlie");

        // Use o Mockito para simular o comportamento do repositório
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(clientRepository.save(Mockito.any(Client.class))).thenReturn(client);

        // Crie uma instância do serviço
        ClientService clientService = new ClientService(clientRepository);

        // Atualize o cliente
        Client updatedClient = new Client();
        updatedClient.setId(1L);
        updatedClient.setName("David");

        Client result = clientService.updateClient(1L, updatedClient);

        // Verifique se o cliente foi atualizado corretamente
        assertEquals("David", result.getName());
    }

    @Test
    public void testDeleteClient() {
        // Use o Mockito para simular o comportamento do repositório
        when(clientRepository.save(Mockito.any(Client.class))).thenReturn(new Client());

        // Crie uma instância do serviço
        ClientService clientService = new ClientService(clientRepository);

        // Crie um cliente
        Client client = new Client();
        client.setName("Eve");

        Client createdClient = clientService.createClient(client);

        // Delete o cliente
        clientService.deleteClient(createdClient.getId());

        // Verifique se o cliente foi excluído
        when(clientRepository.findById(createdClient.getId())).thenReturn(Optional.empty());
        Optional<Client> deletedClient = clientService.getClientById(createdClient.getId());
        assertEquals(false, deletedClient.isPresent());
    }

    @Test
    public void testGetAllClients() {
        // Crie uma lista de clientes de exemplo
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

        // Use o Mockito para simular o comportamento do repositório
        when(clientRepository.findAll()).thenReturn(clientList);

        // Crie uma instância do serviço
        ClientService clientService = new ClientService(clientRepository);

        // Obtenha a lista de todos os clientes
        List<Client> retrievedClients = clientService.getAllClients();

        // Verifique se a lista contém os clientes esperados
        assertEquals(2, retrievedClients.size());
        assertEquals("Alice", retrievedClients.get(0).getName());
        assertEquals("Bob", retrievedClients.get(1).getName());
    }
}
