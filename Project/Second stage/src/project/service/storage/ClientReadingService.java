package project.service.storage;

import project.model.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ClientReadingService {
    private static ClientReadingService instance = new ClientReadingService();

    private ClientReadingService() {}

    public static ClientReadingService getReadingInstance() {
        return instance;
    }

    public Path getPath() {
        return Paths.get("clients.csv");
    }

    public ArrayList<Client> readAllClients() {
        ArrayList<Client> clients = new ArrayList<>();

        File file = new File(String.valueOf(getPath()));
        if(!file.exists())
            return clients;

        try (BufferedReader csvReader = new BufferedReader(new FileReader(String.valueOf(getPath())))) {
            String line;
            while ((line = csvReader.readLine()) != null) {
                String[] data = line.split(",");

                switch (data[2]) {
                    case "Student":
                        clients.add(new Student(Integer.parseInt(data[0]), data[1]));
                        break;
                    case "Child":
                        clients.add(new Child(Integer.parseInt(data[0]), data[1]));
                        break;
                    case "Pensioner":
                        clients.add(new Pensioner(Integer.parseInt(data[0]), data[1]));
                        break;
                    default:
                        clients.add(new Client(Integer.parseInt(data[0]), data[1]));
                        break;
                }
            }
        } catch (IOException e){
                e.printStackTrace();
        }
        return clients;
    }
}
