package project.model;

public class Ticket {
    private static Integer id = 0;
    private final Integer ticketId;
    private Event event;
    private Client client;
    private Seat seat;

    public Ticket(Client client, Event event, Seat seat) {
        this.ticketId = ++id;
        this.event = event;
        this.client = client;
        this.seat = seat;
    }

    public Integer getTicketId() {
        return ticketId;
    }

    public Event getEvent() {
        return event;
    }

    public Client getClient() {
        return client;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", client=" + client +
                ", event=" + event +
                ", seat=" + seat +
                '}';
    }
}
