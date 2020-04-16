package project.main;

import project.model.*;
import project.service.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ClientService clientService = ClientService.getInstance();
        ShowService showService = ShowService.getInstance();
        RoomService roomService = RoomService.getInstance();
        EventService eventService = EventService.getInstance();
        TicketService ticketService = TicketService.getInstance();

        clientService.getExistingClients();
        roomService.getExistingRooms();
        showService.getExistingShows();
        eventService.getExistingEvents();
        ticketService.getExistingTickets();

        int opt;
        do{
            System.out.println("1.Add a new client\n2.Show all clients\n3.Get a client by id\n4.Remove a client and its tickets by id\n" +
                               "5.Get all clients by their discount type\n6.Change clients name by their name\n" +
                               "7.Add a new show\n8.Show all shows\n9.Change the price for a show by id\n" +
                               "10.Add a new room\n11.Show all rooms\n12.Add a new event\n13.Show all events\n" +
                               "14.Change event date by id\n15.Add a new ticket\n16.Show all tickets\n" +
                               "17.Change the seat for a ticket by id\n18.Show earnings for an event by id\n" +
                               "19.Remove a ticket by id\nDefault:Exit");

            Scanner in = new Scanner(System.in);
            opt = in.nextInt();
            in.nextLine();

             switch (opt) {
                case 1:
                    System.out.println("Choose discount type: 1.No discount, 2.Student, 3.Child, 4.Pensioner");
                    int option = in.nextInt();
                    in.nextLine();

                    System.out.println("Provide a name for the new client");
                    String clientName = in.nextLine();

                    switch (option) {
                        case 1:
                            clientService.addClient(new Client(clientName));
                            break;
                        case 2:
                            clientService.addClient(new Student(clientName));
                            break;
                        case 3:
                            clientService.addClient(new Child(clientName));
                            break;
                        case 4:
                            clientService.addClient(new Pensioner(clientName));
                            break;
                        default:
                            System.out.println("This is not a valid option");
                            break;
                    }
                    break;
                case 2:
                    ArrayList<Client> allClients = clientService.getAllClients();

                    for(Client client : allClients) {
                        System.out.println(client);
                    }
                    break;
                case 3:
                    System.out.println("Provide the id");
                    int id = in.nextInt();
                    in.nextLine();

                    try {
                        Client client = clientService.getClientById(id);
                        System.out.println(client);
                    } catch(IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    System.out.println("Provide the id");
                    id = in.nextInt();
                    in.nextLine();

                    try {
                        clientService.removeClientById(id);
                    } catch(IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                    break;
                case 5:
                    System.out.println("Choose discount type: 1.No discount, 2.Student, 3.Child, 4.Pensioner");
                    option = in.nextInt();
                    in.nextLine();

                    switch (option) {
                        case 1:
                            ArrayList<Client> fpClients = clientService.getFullPriceClients();

                            for(Client fpClient : fpClients) {
                                System.out.println(fpClient);
                            }
                            break;
                        case 2:
                            ArrayList<Student> students = clientService.getAllStudents();

                            for(Student student: students) {
                                System.out.println(student);
                            }
                            break;
                        case 3:
                            ArrayList<Child> children = clientService.getAllChildren();

                            for(Child child : children) {
                                System.out.println(child);
                            }
                            break;
                        case 4:
                            ArrayList<Pensioner> pensioners = clientService.getAllPensioners();

                            for(Pensioner pensioner : pensioners) {
                                System.out.println(pensioner);
                            }
                            break;
                        default:
                            System.out.println("This is not a valid option");
                            break;
                    }
                    break;
                 case 6:
                     System.out.println("Provide the actual name");
                     String actualName = in.nextLine();

                     System.out.println("Provide the new name");
                     String newName = in.nextLine();

                     clientService.changeClientsName(actualName, newName);
                     break;
                 case 7:
                     System.out.println("Provide a name for the new show");
                     String showName = in.nextLine();

                     System.out.println("Provide a price for the new show");
                     Double showPrice = in.nextDouble();
                     in.nextLine();

                     showService.addShow(new Show(showName, showPrice));
                     break;
                 case 8:
                     ArrayList<Show> allShows = showService.getAllShows();

                     for(Show show : allShows) {
                         System.out.println(show);
                     }
                     break;
                 case 9:
                     System.out.println("Provide the show id");
                     id = in.nextInt();

                     System.out.println("Provide the new price");
                     Double price = in.nextDouble();
                     in.nextLine();

                     try {
                         showService.changeShowPriceById(id, price);
                     } catch(IllegalArgumentException e) {
                         e.printStackTrace();
                     }
                     break;
                 case 10:
                     System.out.println("Provide a name for the new room");
                     String roomName = in.nextLine();

                     System.out.print("Provide the number of rows and columns\nRows:");
                     Integer rows = in.nextInt();
                     System.out.print("Columns:");
                     Integer columns = in.nextInt();
                     in.nextLine();

                     roomService.addRoom(new Room(roomName, rows, columns));
                     break;
                 case 11:
                     ArrayList<Room> allRooms = roomService.getAllRooms();

                     for(Room room : allRooms) {
                         System.out.println(room);
                     }
                     break;
                 case 12:
                     System.out.println("Provide the show id");
                     int showId = in.nextInt();

                     System.out.println("Provide the room id");
                     Integer roomId = in.nextInt();

                     System.out.println("Provide a date in dd/MM/yyyy format");
                     String date = in.next();
                     in.nextLine();

                     try {
                         Show show = showService.getShowById(showId);
                         Room room = roomService.getRoomById(roomId);

                         eventService.addEvent(new Event(show, room, date));
                     } catch (ParseException | IllegalArgumentException e) {
                         e.printStackTrace();
                     }
                     break;
                 case 13:
                     ArrayList<Event> allEvents = eventService.getAllEvents();

                     for(Event event : allEvents) {
                         System.out.println(event);
                     }
                     break;
                 case 14:
                     System.out.println("Provide the event id");
                     int eventId = in.nextInt();

                     try {
                         System.out.println("Provide the date in dd/MM/yyyy format");
                         Date newDate = new SimpleDateFormat("dd/MM/yyyy").parse(in.next());
                         in.nextLine();

                         eventService.changeEventDateById(eventId, newDate);
                     } catch (ParseException | IllegalArgumentException e) {
                         e.printStackTrace();
                     }
                     break;
                 case 15:
                     System.out.println("Provide the client id");
                     int clientId = in.nextInt();

                     System.out.println("Provide the event id");
                     eventId = in.nextInt();

                     try {
                         Client client = clientService.getClientById(clientId);
                         Event event = eventService.getEventById(eventId);

                         System.out.println("Type 1 if you want a certain seat, another number if you don't");
                         int choice = in.nextInt();

                         if (choice == 1) {
                             System.out.print("Row:");
                             Integer row = in.nextInt();

                             System.out.print("Column:");
                             Integer column = in.nextInt();
                             in.nextLine();

                             ticketService.addTicket(new Ticket(client, event, new Seat(row, column)));
                         } else {
                             ticketService.addTicket(client, event);
                         }
                     } catch(IllegalArgumentException e) {
                         e.printStackTrace();
                     }
                     break;
                 case 16:
                     ArrayList<Ticket> tickets = ticketService.getAllTickets();

                     for(Ticket ticket : tickets) {
                         System.out.println(ticket);
                     }
                     break;
                 case 17:
                     System.out.println("Provide the ticket id");
                     int ticketId = in.nextInt();

                     System.out.print("Provide the new seat\nRow:");
                     Integer row = in.nextInt();

                     System.out.print("Column:");
                     Integer column = in.nextInt();
                     in.nextLine();

                     try {
                         ticketService.changeTicketSeatById(ticketId, new Seat(row, column));
                     } catch (IllegalArgumentException e) {
                         e.printStackTrace();
                     }
                     break;
                 case 18:
                     System.out.println("Provide the event id");
                     eventId = in.nextInt();
                     in.nextLine();

                     try {
                         System.out.println(ticketService.getEventEarningsById(eventId));
                     } catch(IllegalArgumentException e) {
                         e.printStackTrace();
                     }
                     break;
                 case 19:
                     System.out.println("Provide the ticket id");
                     ticketId = in.nextInt();
                     in.nextLine();

                     try {
                         ticketService.removeTicketById(ticketId);
                     } catch(IllegalArgumentException e) {
                         e.printStackTrace();
                     }
                     break;
                default:
                    opt = 0;
                    break;
            }
        } while(opt != 0);

        /*
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

        try {
            showService.changeShowPriceById(2, 45.0);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        for(Show show : shows) {
            System.out.println(show.toString());
        }

        EventService eventService = EventService.getInstance();

        try {
            eventService.addEvent(new Event(showService.getShowById(1), roomService.getRoomById(2),"22/7/2019"));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.print("\nTry to assign two events in a room on the same date:");
            eventService.addEvent(new Event(showService.getShowById(2), roomService.getRoomById(2),"22/7/2019"));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            eventService.addEvent(new Event(showService.getShowById(2), roomService.getRoomById(2),"18/3/2021"));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nShow all events:");

        ArrayList<Event> events = eventService.getAllEvents();

        for(Event event : events) {
            System.out.println(event);
        }

        System.out.println("\nChange the room for an event(In this case: eventId=1, actualRoomId=2, newRoomId=3):");

        try {
            eventService.changeEventRoomById(1, roomService.getRoomById(3));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        for(Event event : events) {
            System.out.println(event);
        }

        System.out.println("\nChange the date for an event(In this case: eventId=3):");

        try {
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse("22/08/2020");

            try {
                eventService.changeEventDateById(3, date);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } catch (ParseException e) {
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

        try {
            ticketService.addTicket(new Ticket(clientService.getClientById(2), eventService.getEventById(3), new Seat(2,3)));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            ticketService.addTicket(clientService.getClientById(1), eventService.getEventById(1));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
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

        try {
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
    */
    }
}
