package project.repository;

import project.model.Event;

import java.util.ArrayList;
import java.util.Date;

public class EventRepository {
    private ArrayList<Event> eventDB;

    public EventRepository() {
        eventDB = new ArrayList<>();
    }

    public void add(Event event) {
        eventDB.add(event);
    }

    public Event getEventById(Integer id) {
        for(Event event : eventDB) {
            if(event.getEventId().equals(id)) {
                return event;
            }
        }
        return null;
    }

    public ArrayList<Event> getAll() {
        return eventDB;
    }

    public ArrayList<Event> getAllByShowId(Integer id) {
        ArrayList<Event> events = new ArrayList<>();

        for(Event event : eventDB) {
            if(event.getShow().getShowId().equals(id)){
                events.add(event);
            }
        }
        return events;
    }

    public ArrayList<Event> getAllByRoomId(Integer id) {
        ArrayList<Event> events = new ArrayList<>();

        for(Event event : eventDB) {
            if(event.getRoom().getRoomId().equals(id)){
                events.add(event);
            }
        }
        return events;
    }

    public ArrayList<Event> getAllByDate(Date date) {
        ArrayList<Event> events = new ArrayList<>();

        for(Event event : eventDB) {
            if(event.getDate().equals(date)){
                events.add(event);
            }
        }
        return events;
    }
}
