package project.service.storage;

import project.model.*;
import project.service.ClientService;
import project.service.EventService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class TicketReadingService {
    private static TicketReadingService instance = new TicketReadingService();
    ClientService clientService = ClientService.getInstance();
    EventService eventService = EventService.getInstance();

    private TicketReadingService() {}

    public static TicketReadingService getReadingInstance() {
        return instance;
    }

    public Path getPath() {
        return Paths.get("tickets.csv");
    }

    public ArrayList<Ticket> readAllTickets() {
        ArrayList<Ticket> tickets = new ArrayList<>();

        File file = new File(String.valueOf(getPath()));
        if(!file.exists())
            return tickets;

        try (BufferedReader csvReader = new BufferedReader(new FileReader(String.valueOf(getPath())))) {
            String line;
            while ((line = csvReader.readLine()) != null) {
                String[] data = line.split(",");

                tickets.add(new Ticket(parseInt(data[0]), clientService.getClientById(parseInt(data[1])), eventService.getEventById(parseInt(data[2])),
                                        new Seat(Integer.parseInt(data[3]),Integer.parseInt(data[4]))));
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return tickets;
    }
}
