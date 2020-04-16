package project.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static sun.swing.MenuItemLayoutHelper.max;

public class Event {
    private static Integer id = 0;
    private final Integer eventId;
    private Show show;
    private Room room ;
    private Date date;

    public Event(Show show, Room room, String date) throws ParseException {
        this.show = show;
        this.room = new Room(room);
        this.date = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        this.eventId = ++id;
    }

    public Event(Integer eventId, Show show, Room room, String date) throws ParseException {
        this.show = show;
        this.room = new Room(room);
        this.date = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        this.eventId = eventId;
        id = max(id, eventId);
    }

    public Event(Show show, Room room, Date date) {
        this.show = show;
        this.room = new Room(room);
        this.date = date;
        this.eventId = ++id;
    }

    public Date getDate() {
        return date;
    }

    public Room getRoom() {
        return room;
    }

    public Show getShow() {
        return show;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setRoom(Room room) {
        this.room = new Room(room);
    }

    public void setShow(Show show) {
        this.show = show;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", show=" + show +
                ", room=" + room +
                ", date=" + date +
                '}';
    }
}
