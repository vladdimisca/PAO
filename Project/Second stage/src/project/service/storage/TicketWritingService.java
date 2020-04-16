package project.service.storage;

import project.model.Ticket;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TicketWritingService {
    private static TicketWritingService instance = new TicketWritingService();

    private TicketWritingService() {}

    public static TicketWritingService getWritingInstance() {
        return instance;
    }

    public Path getPath() {
        return Paths.get("tickets.csv");
    }

    public void writeTicket(Ticket ticket) {
        try (FileWriter csvWriter = new FileWriter(String.valueOf(getPath()), true)) {
            List<String> line =  Arrays.asList(Integer.toString(ticket.getTicketId()), Integer.toString(ticket.getClient().getClientId()),
                                               Integer.toString(ticket.getEvent().getEventId()), Integer.toString(ticket.getSeat().getRow()),
                                               Integer.toString(ticket.getSeat().getColumn()));
            csvWriter.append(String.join(",", line));
            csvWriter.append("\n");
            csvWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateTickets(ArrayList<Ticket> tickets) {
        try (FileWriter csvWriter = new FileWriter(String.valueOf(getPath()), false)) {
            for(Ticket ticket : tickets) {
                List<String> line = Arrays.asList(Integer.toString(ticket.getTicketId()), Integer.toString(ticket.getClient().getClientId()),
                                                  Integer.toString(ticket.getEvent().getEventId()), Integer.toString(ticket.getSeat().getRow()),
                                                  Integer.toString(ticket.getSeat().getColumn()));
                csvWriter.append(String.join(",", line));
                csvWriter.append("\n");
                csvWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
