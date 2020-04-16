package project.service;

import project.model.*;
import project.repository.ClientRepository;
import project.service.storage.AuditService;
import project.service.storage.ClientReadingService;
import project.service.storage.ClientWritingService;

import java.util.ArrayList;


public class ClientService {
    private ClientRepository clientRepository = new ClientRepository();
    private static ClientService instance = new ClientService();
    ClientWritingService clientWritingService = ClientWritingService.getWritingInstance();
    AuditService auditService = AuditService.getInstance();

    private ClientService() { }

    public static ClientService getInstance() {
        return instance;
    }

    public void addClient(Client client) {
        auditService.writeAction("Add a new client", auditService.getTimestamp());

        clientRepository.add(client);
        clientWritingService.writeClient(client);
    }

    public void addExistingClient(Client client) {
        auditService.writeAction("Add an existing client from csv", auditService.getTimestamp());
        clientRepository.add(client);
    }

    public void getExistingClients() {
        auditService.writeAction("Get all existing clients from csv", auditService.getTimestamp());

        ClientReadingService clientReadingService = ClientReadingService.getReadingInstance();
        ArrayList<Client> clients = clientReadingService.readAllClients();

        for(Client client: clients) {
            addExistingClient(client);
        }
    }

    public Client getClientById(Integer id) {
        auditService.writeAction("Get a client by id", auditService.getTimestamp());

        Client client = clientRepository.getClientById(id);

        if(client == null)
            throw new IllegalArgumentException("This id does not exist!");

        return client;
    }

    public void changeClientNameById(Integer id, String newName) {
        auditService.writeAction("Change a client name by id", auditService.getTimestamp());

        Client client = clientRepository.getClientById(id);
        client.setClientName(newName);

        ArrayList<Client> clients = getAllClients();
        clientWritingService.updateClients(clients);
    }

    public ArrayList<Client> getClientsByName(String name) {
        auditService.writeAction("Get clients by name", auditService.getTimestamp());

        return clientRepository.getAllByName(name);
    }

    public void changeClientsName(String actualName, String newName) {
        auditService.writeAction("Change clients name by actual name", auditService.getTimestamp());

        ArrayList<Client> clients= clientRepository.getAllByName(actualName);

        for(Client client : clients)
            client.setClientName(newName);

        ArrayList<Client> updatedClients= clientRepository.getAll();
        clientWritingService.updateClients(updatedClients);
    }

    public ArrayList<Client> getAllClients() {
        auditService.writeAction("Get all clients", auditService.getTimestamp());
        return clientRepository.getAll();
    }

    public ArrayList<Student> getAllStudents() {
        auditService.writeAction("Get all students", auditService.getTimestamp());
        return clientRepository.getAllStudents();
    }

    public ArrayList<Child> getAllChildren() {
        auditService.writeAction("Get all children", auditService.getTimestamp());
        return clientRepository.getAllChildren();
    }

    public ArrayList<Pensioner> getAllPensioners() {
        auditService.writeAction("Get all pensioners", auditService.getTimestamp());
        return clientRepository.getAllPensioners();
    }

    public ArrayList<Client> getFullPriceClients() {
        auditService.writeAction("Get all full price clients", auditService.getTimestamp());
        return clientRepository.getAllFullPriceClients();
    }

    public void removeClientById(Integer id) {
        auditService.writeAction("Remove a client by id", auditService.getTimestamp());

        TicketService ticketService = TicketService.getInstance();
        Client client = clientRepository.getClientById(id);

        ArrayList<Ticket> tickets = ticketService.getTicketsByClientId(id);

        for(Ticket ticket : tickets) {
            ticketService.removeTicketById(ticket.getTicketId());
        }

        clientRepository.remove(client);

        ArrayList<Client> clients = getAllClients();
        clientWritingService.updateClients(clients);
    }
}
