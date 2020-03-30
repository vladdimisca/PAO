package project.main;

import project.model.*;
import project.service.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws Exception {
        ClientService clientService = ClientService.getInstance();

        clientService.addClient(new Client("Ion"));
        clientService.addClient(new Student("Vlad"));
        clientService.addClient(new Child("Inocentiu"));
        clientService.addClient(new Student("Covidiu"));
        clientService.addClient(new Student("Vlad"));
        clientService.addClient(new Pensioner("Decebal Popescu"));

        ArrayList<Client> clients = clientService.getAllClients();

        System.out.println("Show all clients:");

        for(Client client : clients) {
            System.out.println(client);
        }

        System.out.println("\nShow all students:");

        ArrayList<Student> students = clientService.getAllStudents();

        for(Student student : students) {
            System.out.println(student);
        }

        System.out.println("\nShow the client with clientId=2:");
        System.out.println(clientService.getClientById(2));

        System.out.println("\nChange the name for the client with clientId=2 in Covidiu and then show all clients with this name:");

        clientService.changeClientNameById(2, "Covidiu");

        ArrayList<Client> clientsByName = clientService.getClientsByName("Covidiu");

        for(Client client : clientsByName) {
            System.out.println(client.toString());
        }

        RoomService roomService = RoomService.getInstance();

        roomService.addRoom(new Room("Somewhere", 25, 30));
        roomService.addRoom(new Room("Elsewhere", 16, 23));
        roomService.addRoom(new Room("My apartment", 15, 17));

        System.out.println("\nShow all rooms:");

        ArrayList<Room> rooms = roomService.getAllRooms();

        for(Room room : rooms) {
            System.out.println(room);
        }

        ShowService showService = ShowService.getInstance();

        showService.addShow(new Show("Karaoke night", 40.0));
        showService.addShow(new Show("Big show", 30.0));
        showService.addShow(new Show("Oache party", 10.0));

        System.out.println("\nShow all shows:");

        ArrayList<Show> shows = showService.getAllShows();

        for(Show show : shows) {
            System.out.println(show.toString());
        }

        System.out.println("\nChange the price for the show with showId=2 and then show all shows:");

        try{
            showService.changeShowPriceById(2, 45.0);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        for(Show show : shows) {
            System.out.println(show.toString());
        }

        EventService eventService = EventService.getInstance();

        try{
            eventService.addEvent(new Event(showService.getShowById(1), roomService.getRoomById(2),"22/7/2019"));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try{
            System.out.print("\nTry to assign two events in a room on the same date:");
            eventService.addEvent(new Event(showService.getShowById(2), roomService.getRoomById(2),"22/7/2019"));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try{
            eventService.addEvent(new Event(showService.getShowById(2), roomService.getRoomById(2),"18/3/2021"));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nShow all events:");

        ArrayList<Event> events = eventService.getAllEvents();

        for(Event event : events) {
            System.out.println(event);
        }

        System.out.println("\nCnange the room for an event(In this case: eventId=1, actualRoomId=2, newRoomId=3):");

        try{
            eventService.changeEventRoomById(1, roomService.getRoomById(3));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        for(Event event : events) {
            System.out.println(event);
        }

        System.out.println("\nCnange the date for an event(In this case: eventId=3):");

        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("22/05/2020");

        try{
            eventService.changeEventDateById(3, date);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        for(Event event : events) {
            System.out.println(event);
        }

        ArrayList<Event> eventsByShowId = eventService.getEventsByShowId(1);

        System.out.println("\nShow events with a certain showId(1, in this case):");

        for(Event event : eventsByShowId) {
            System.out.println(event);
        }

        TicketService ticketService = TicketService.getInstance();

        try{
            ticketService.addTicket(new Ticket(clientService.getClientById(2), eventService.getEventById(3), new Seat(2,3)));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try{
            ticketService.addTicket(clientService.getClientById(1), eventService.getEventById(1));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try{
            ticketService.addTicket(clientService.getClientById(4), eventService.getEventById(1));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        ArrayList<Ticket> tickets = ticketService.getAllTickets();

        System.out.println("\nShow all tickets:");

        for(Ticket ticket : tickets) {
            System.out.println(ticket);
        }

        System.out.println("\nCheck if you can assign a seat for a certain event:");

        if(ticketService.checkLegalTicket(new Ticket(clientService.getClientById(3), eventService.getEventById(1), new Seat(1,2)))) {
            System.out.println("It is possible to add a ticket with clientId=3, eventId=1 and seat(row=1, column=2)");
        } else {
            System.out.println("It is not possible to add a ticket with clientId=3, eventId=1 and seat(row=1, column=2)");
        }

        if(ticketService.checkLegalTicket(new Ticket(clientService.getClientById(2), eventService.getEventById(3), new Seat(2,3)))) {
            System.out.println("It is possible to add a ticket with clientId=2, eventId=3 and seat(row=2, column=3)");
        } else {
            System.out.println("It is not possible to add a ticket with clientId=2, eventId=3 and seat(row=2, column=3)");
        }

        System.out.println("\nRemove the client with clientId=2 and the tickets assigned with his id and then show all clients and all tickets:");

        try{
            clientService.removeClientById(2);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        for(Client client : clients) {
            System.out.println(client);
        }

        System.out.println();

        for(Ticket ticket : tickets) {
            System.out.println(ticket);
        }

        System.out.println("\nChange the seat for ticket with ticketId=2 in (3, 5) and then show all tickets:");

        ticketService.changeTicketSeatById(2, new Seat(3,5));

        for(Ticket ticket : tickets) {
            System.out.println(ticket);
        }

        System.out.println("\nShow earnings for a certain event(In this case, eventId=1):\n" + ticketService.getEventEarningsById(1));

    }
}
