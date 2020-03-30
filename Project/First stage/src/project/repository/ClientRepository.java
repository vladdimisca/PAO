package project.repository;

import project.model.Client;
import project.model.Student;
import project.model.Pensioner;
import project.model.Child;

import java.util.ArrayList;

public class ClientRepository {
    private ArrayList<Client> clientDB;

    public ClientRepository() {
        clientDB = new ArrayList<>();
    }

    public void add(Client client) {
        clientDB.add(client);
    }

    public Client getClientById(Integer id) {
        for(Client client : clientDB) {
            if(client.getClientId().equals(id)) {
                return client;
            }
        }
        return null;
    }

    public ArrayList<Client> getAllByName(String name) {
        ArrayList<Client> clients = new ArrayList<>();

        for(Client client : clientDB) {
            if(client.getClientName().equals(name)){
                clients.add(client);
            }
        }
        return clients;
    }

    public ArrayList<Client> getAll() {
       return clientDB;
    }

    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> students = new ArrayList<>();

        for(Client client : clientDB) {
            if (client instanceof Student) {
                Student student = (Student) client;
                students.add(student);
            }
        }
        return students;
    }

    public ArrayList<Pensioner> getAllPensioners() {
        ArrayList<Pensioner> pensioners = new ArrayList<>();

        for(Client client : clientDB) {
            if (client instanceof Pensioner) {
                Pensioner pensioner = (Pensioner) client;
                pensioners.add(pensioner);
            }
        }
        return pensioners;
    }

    public ArrayList<Child> getAllChildren() {
        ArrayList<Child> children = new ArrayList<>();

        for(Client client : clientDB) {
            if (client instanceof Child) {
                Child child = (Child) client;
                children.add(child);
            }
        }
        return children;
    }

    public ArrayList<Client> getAllFullPriceClients() {
        ArrayList<Client> fullPriceClients = new ArrayList<>();

        for(Client client : clientDB) {
            if (!(client instanceof Child) && !(client instanceof Pensioner) && !(client instanceof Student)) {
                fullPriceClients.add(client);
            }
        }
        return fullPriceClients;
    }

    public void remove(Client client) {
        clientDB.remove(client);
    }
}
