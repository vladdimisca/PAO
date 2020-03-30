package project.service;

import project.model.*;
import project.repository.ClientRepository;

import java.util.ArrayList;

public class ClientService {
    private ClientRepository clientRepository = new ClientRepository();
    private static ClientService instance = new ClientService();

    private ClientService() { }

    public static ClientService getInstance() {
        return instance;
    }

    public void addClient(Client client) {
        clientRepository.add(client);
    }

    public Client getClientById(Integer id) {
        return clientRepository.getClientById(id);
    }

    public void changeClientNameById(Integer id, String newName) {
        Client client = clientRepository.getClientById(id);

        if(client != null) {
            client.setClientName(newName);
        } else {
            throw new IllegalArgumentException("This id does not exist!");
        }
    }

    public ArrayList<Client> getClientsByName(String name) {
        return clientRepository.getAllByName(name);
    }

    public void changeClientsName(String actualName, String newName) {
        ArrayList<Client> clients= clientRepository.getAllByName(actualName);

        for(Client client : clients)
            client.setClientName(newName);
    }

    public ArrayList<Client> getAllClients() {
        return clientRepository.getAll();
    }

    public ArrayList<Student> getAllStudents() {
        return clientRepository.getAllStudents();
    }

    public ArrayList<Child> getAllChildren() {
        return clientRepository.getAllChildren();
    }

    public ArrayList<Pensioner> getAllPensioners() {
        return clientRepository.getAllPensioners();
    }

    public ArrayList<Client> getFullPriceClients() {
        return clientRepository.getAllFullPriceClients();
    }

    public void removeClientById(Integer id) {
        TicketService ticketService = TicketService.getInstance();
        Client client = clientRepository.getClientById(id);

        if(client == null) {
            throw new IllegalArgumentException("This clientId does not exist!");
        }

        ArrayList<Ticket> tickets = ticketService.getTicketsByClientId(id);

        for(Ticket ticket : tickets) {
            ticketService.removeTicketById(ticket.getTicketId());
        }

        clientRepository.remove(client);
    }
}
