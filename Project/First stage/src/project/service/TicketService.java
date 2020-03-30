package project.service;

import project.model.*;
import project.repository.TicketRepository;

import java.util.ArrayList;

public class TicketService {
    private TicketRepository ticketRepository = new TicketRepository();
    private static TicketService instance = new TicketService();

    private TicketService() { }

    public static TicketService getInstance() {
        return instance;
    }

    public Boolean checkLegalTicket(Ticket ticket) {
        Integer desiredRow = ticket.getSeat().getRow();
        Integer desiredColumn = ticket.getSeat().getColumn();

        if(desiredRow >= ticket.getEvent().getRoom().getHallRows() || desiredColumn >= ticket.getEvent().getRoom().getHallCols()) {
            return false;
        }

        return !ticket.getEvent().getRoom().checkHallSeat(desiredRow, desiredColumn);
    }

    public void addTicket(Ticket ticket) {
        if (checkLegalTicket(ticket)) {
            ticket.getEvent().getRoom().setHallSeat(ticket.getSeat().getRow(), ticket.getSeat().getColumn(), true);
            ticketRepository.add(ticket);
        } else {
            throw new IllegalArgumentException("The chosen seat is busy!");
        }
    }

    public void addTicket(Client client, Event event) {
        Seat seat = event.getRoom().getFreeSeat();

        if(seat != null) {
            ticketRepository.add(new Ticket(client, event, seat));
        } else {
            throw new IllegalArgumentException("There is no free seat!");
        }

    }

    public Ticket getTicketById(Integer id) {
        return ticketRepository.getTicketById(id);
    }

    public ArrayList<Ticket> getAllTickets() {
        return ticketRepository.getAll();
    }

    public ArrayList<Ticket> getTicketsByEventId(Integer id) {
        return ticketRepository.getAllByEventId(id);
    }

    public ArrayList<Ticket> getTicketsByClientId(Integer id) {
        return ticketRepository.getAllByClientId(id);
    }

    public Double getEventEarningsById(Integer id) {
        EventService eventService = EventService.getInstance();
        Event event = eventService.getEventById(id);

        Double earnings = 0.0;

        ArrayList<Ticket> tickets = ticketRepository.getAllByEventId(event.getEventId());

        for(Ticket ticket : tickets) {
            if(ticket.getClient() instanceof Child) {
                Child child = (Child) ticket.getClient();
                earnings += child.getPrice(event.getShow().getPrice());
            } else if (ticket.getClient() instanceof Student) {
                Student student = (Student) ticket.getClient();
                earnings += student.getPrice(event.getShow().getPrice());
            } else if (ticket.getClient() instanceof Pensioner) {
                Pensioner pensioner = (Pensioner) ticket.getClient();
                earnings += pensioner.getPrice(event.getShow().getPrice());
            } else {
                earnings += ticket.getClient().getPrice(event.getShow().getPrice());
            }
        }
        return earnings;
    }

    public void changeTicketSeatById(Integer id, Seat seat) {
        Ticket ticket = getTicketById(id);

        if(ticket == null) {
            throw new IllegalArgumentException("This ticketId does not exist!");
        }

        if(checkLegalTicket(new Ticket(ticket.getClient(), ticket.getEvent(), seat))) {
            ticket.getEvent().getRoom().setHallSeat(ticket.getSeat().getRow(), ticket.getSeat().getColumn(), false);
            ticket.getEvent().getRoom().setHallSeat(seat.getRow(), seat.getColumn(), true);
            ticket.setSeat(seat);
        } else {
            throw new IllegalArgumentException("This seat is busy!");
        }
    }

    public void removeTicketById(Integer id) {
        Ticket ticket = ticketRepository.getTicketById(id);

        if(ticket == null) {
            throw new IllegalArgumentException("This ticketId does not exist!");
        }

        Seat seat = ticket.getSeat();

        ticket.getEvent().getRoom().setHallSeat(seat.getRow(), seat.getColumn(), false);

        ticketRepository.remove(ticket);
    }
}
