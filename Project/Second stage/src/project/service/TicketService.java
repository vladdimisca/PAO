package project.service;

import project.model.*;
import project.repository.TicketRepository;
import project.service.storage.AuditService;
import project.service.storage.TicketReadingService;
import project.service.storage.TicketWritingService;

import java.util.ArrayList;

public class TicketService {
    private TicketRepository ticketRepository = new TicketRepository();
    private static TicketService instance = new TicketService();
    TicketWritingService ticketWritingService = TicketWritingService.getWritingInstance();
    AuditService auditService = AuditService.getInstance();

    private TicketService() { }

    public static TicketService getInstance() {
        return instance;
    }

    public Boolean checkLegalTicket(Ticket ticket) {
        auditService.writeAction("Check if a ticket could be assigned", auditService.getTimestamp());

        Integer desiredRow = ticket.getSeat().getRow();
        Integer desiredColumn = ticket.getSeat().getColumn();

        if(desiredRow >= ticket.getEvent().getRoom().getHallRows() || desiredColumn >= ticket.getEvent().getRoom().getHallCols()) {
            return false;
        }

        return !ticket.getEvent().getRoom().checkHallSeat(desiredRow, desiredColumn);
    }

    public void addTicket(Ticket ticket) {
        auditService.writeAction("Add a new ticket", auditService.getTimestamp());

        if (checkLegalTicket(ticket)) {
            ticket.getEvent().getRoom().setHallSeat(ticket.getSeat().getRow(), ticket.getSeat().getColumn(), true);
            ticketRepository.add(ticket);
            ticketWritingService.writeTicket(ticket);
        } else {
            throw new IllegalArgumentException("The chosen seat is busy or does not exist!");
        }
    }

    public void addTicket(Client client, Event event) {
        auditService.writeAction("Add a new ticket", auditService.getTimestamp());

        Seat seat = event.getRoom().getFreeSeat();

        if(seat != null) {
            Ticket ticket = new Ticket(client, event, seat);
            ticketRepository.add(ticket);
            ticketWritingService.writeTicket(ticket);
        } else {
            throw new IllegalArgumentException("There is no free seat!");
        }
    }

    public void addExistingTicket(Ticket ticket) {
        auditService.writeAction("Add an existing ticket from csv", auditService.getTimestamp());

        if (checkLegalTicket(ticket)) {
            ticket.getEvent().getRoom().setHallSeat(ticket.getSeat().getRow(), ticket.getSeat().getColumn(), true);
            ticketRepository.add(ticket);
        } else {
            throw new IllegalArgumentException("The chosen seat is busy or does not exist!");
        }
    }

    public void getExistingTickets() {
        auditService.writeAction("Get all existing tickets from csv", auditService.getTimestamp());

        TicketReadingService ticketReadingService = TicketReadingService.getReadingInstance();
        ArrayList<Ticket> tickets = ticketReadingService.readAllTickets();

        for(Ticket ticket : tickets) {
            addExistingTicket(ticket);
        }
    }

    public Ticket getTicketById(Integer id) {
        auditService.writeAction("Get a ticket by id", auditService.getTimestamp());
        return ticketRepository.getTicketById(id);
    }

    public ArrayList<Ticket> getAllTickets() {
        auditService.writeAction("Get all tickets", auditService.getTimestamp());
        return ticketRepository.getAll();
    }

    public ArrayList<Ticket> getTicketsByEventId(Integer id) {
        auditService.writeAction("Get tickets by event id", auditService.getTimestamp());
        return ticketRepository.getAllByEventId(id);
    }

    public ArrayList<Ticket> getTicketsByClientId(Integer id) {
        auditService.writeAction("Get tickets by client id", auditService.getTimestamp());
        return ticketRepository.getAllByClientId(id);
    }

    public Double getEventEarningsById(Integer id) {
        auditService.writeAction("Get an event earnings by id", auditService.getTimestamp());

        EventService eventService = EventService.getInstance();
        Event event = eventService.getEventById(id);

        Double earnings = 0.0;

        ArrayList<Ticket> tickets = ticketRepository.getAllByEventId(event.getEventId());

        for(Ticket ticket : tickets) {
            earnings += ticket.getClient().getPrice(event.getShow().getPrice());
        }
        return earnings;
    }

    public void changeTicketSeatById(Integer id, Seat seat) {
        auditService.writeAction("Change a ticket seat by id", auditService.getTimestamp());

        Ticket ticket = getTicketById(id);

        if(checkLegalTicket(new Ticket(ticket.getClient(), ticket.getEvent(), seat))) {
            ticket.getEvent().getRoom().setHallSeat(ticket.getSeat().getRow(), ticket.getSeat().getColumn(), false);
            ticket.getEvent().getRoom().setHallSeat(seat.getRow(), seat.getColumn(), true);
            ticket.setSeat(seat);

            ArrayList<Ticket> tickets = getAllTickets();
            ticketWritingService.updateTickets(tickets);
        } else {
            throw new IllegalArgumentException("This seat is busy or does not exist!");
        }
    }

    public void removeTicketById(Integer id) {
        auditService.writeAction("Remove a ticket by id", auditService.getTimestamp());

        Ticket ticket = ticketRepository.getTicketById(id);
        Seat seat = ticket.getSeat();

        ticket.getEvent().getRoom().setHallSeat(seat.getRow(), seat.getColumn(), false);

        ticketRepository.remove(ticket);

        ArrayList<Ticket> tickets = getAllTickets();
        ticketWritingService.updateTickets(tickets);
    }
}
