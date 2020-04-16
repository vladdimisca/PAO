package project.repository;

import project.model.Ticket;

import java.util.ArrayList;

public class TicketRepository {
    private ArrayList<Ticket> ticketDB;

    public TicketRepository() {
        ticketDB = new ArrayList<>();
    }

    public void add(Ticket ticket) {
        ticketDB.add(ticket);
    }

    public Ticket getTicketById(Integer id) {
        for(Ticket ticket : ticketDB) {
            if(ticket.getTicketId().equals(id)) {
                return ticket;
            }
        }
        return null;
    }

    public ArrayList<Ticket> getAll() {
        return ticketDB;
    }

    public ArrayList<Ticket> getAllByEventId(Integer id) {
        ArrayList<Ticket> tickets = new ArrayList<>();

        for(Ticket ticket : ticketDB) {
            if(ticket.getEvent().getEventId().equals(id)){
                tickets.add(ticket);
            }
        }
        return tickets;
    }

    public ArrayList<Ticket> getAllByClientId(Integer id) {
        ArrayList<Ticket> tickets = new ArrayList<>();

        for(Ticket ticket : ticketDB) {
            if(ticket.getClient().getClientId().equals(id)){
                tickets.add(ticket);
            }
        }
        return tickets;
    }

    public void remove(Ticket ticket) {
        ticketDB.remove(ticket);
    }
}
