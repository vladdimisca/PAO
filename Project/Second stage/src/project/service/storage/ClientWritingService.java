package project.service.storage;

import project.model.Client;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ClientWritingService {
    private static ClientWritingService instance = new ClientWritingService();

    private ClientWritingService() {}

    public static ClientWritingService getWritingInstance() {
        return instance;
    }

    public Path getPath() {
        return Paths.get("clients.csv");
    }

    public void writeClient(Client client) {
        try (FileWriter csvWriter = new FileWriter(String.valueOf(getPath()), true)) {
            List<String> line =  Arrays.asList(Integer.toString(client.getClientId()), client.getClientName(), client.getDiscountType());
            csvWriter.append(String.join(",", line));
            csvWriter.append("\n");
            csvWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateClients(ArrayList<Client> clients) {
        try (FileWriter csvWriter = new FileWriter(String.valueOf(getPath()), false)) {
            for(Client client : clients) {
                List<String> line = Arrays.asList(Integer.toString(client.getClientId()), client.getClientName(), client.getDiscountType());
                csvWriter.append(String.join(",", line));
                csvWriter.append("\n");
                csvWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
