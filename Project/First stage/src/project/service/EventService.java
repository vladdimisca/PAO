package project.service;

import project.model.Event;
import project.model.Room;
import project.model.Show;
import project.repository.EventRepository;

import java.util.ArrayList;
import java.util.Date;

public class EventService {
    private EventRepository eventRepository = new EventRepository();
    private static EventService instance = new EventService();

    private EventService() { }

    public static EventService getInstance() {
        return instance;
    }

    public Boolean checkLegalEvent(Event event) {
        ArrayList<Event> eventsByDate = getEventsByDate(event.getDate());//search for all events from DB which take place on the same date

        for(Event e : eventsByDate) {
            if(e.getRoom().equals(event.getRoom()))
                return false;
        }
        return true;
    }

    public void addEvent(Event event) {
        if(checkLegalEvent(event))
            eventRepository.add(event);
        else {
            throw new IllegalArgumentException("\nCan't assign two shows in a room on the same date!  Didn't add the event!");
        }
    }

    public Event getEventById(Integer id) {
        return eventRepository.getEventById(id);
    }

    public ArrayList<Event> getAllEvents() {
        return eventRepository.getAll();
    }

    public void changeEventShowById(Integer id, Show show) {
        Event event = getEventById(id);

        event.setShow(show);
    }

    public void changeEventRoomById(Integer id, Room room) {
        Event event = getEventById(id);

        if(event == null) {
            throw new IllegalArgumentException("There is no event with this id!");
        }

        if(checkLegalEvent(new Event(event.getShow(), room, event.getDate()))) {
            event.setRoom(room);
        } else {
            throw new IllegalArgumentException("\nCan't assign two shows in the a room on the same date! Didn't change event room!");
        }
    }

    public void changeEventDateById(Integer id, Date date) {
        Event event = getEventById(id);

        if(event == null) {
            throw new IllegalArgumentException("There is no event with this id!");
        }

        if(checkLegalEvent(new Event(event.getShow(), event.getRoom(), date))) {
            event.setDate(date);
        } else {
            throw new IllegalArgumentException("\nCan't assign two shows in the a room on the same date! Didn't change event date!");
        }
    }

    public ArrayList<Event> getEventsByShowId(Integer id) {
        return eventRepository.getAllByShowId(id);
    }

    public ArrayList<Event> getEventsByRoomId(Integer id) {
        return eventRepository.getAllByRoomId(id);
    }

    public ArrayList<Event> getEventsByDate(Date date) {
        return eventRepository.getAllByDate(date);
    }
}
