package project.service;

import project.model.Event;
import project.model.Room;
import project.model.Show;
import project.repository.EventRepository;
import project.service.storage.AuditService;
import project.service.storage.EventReadingService;
import project.service.storage.EventWritingService;

import java.util.ArrayList;
import java.util.Date;

public class EventService {
    private EventRepository eventRepository = new EventRepository();
    private static EventService instance = new EventService();
    EventWritingService eventWritingService = EventWritingService.getWritingInstance();
    AuditService auditService = AuditService.getInstance();

    private EventService() { }

    public static EventService getInstance() {
        return instance;
    }

    public Boolean checkLegalEvent(Event event) {
        auditService.writeAction("Check if an event can take place", auditService.getTimestamp());

        ArrayList<Event> eventsByDate = getEventsByDate(event.getDate());//search for all events from DB which take place on the same date

        for(Event e : eventsByDate) {
            if(e.getRoom().equals(event.getRoom()))
                return false;
        }
        return true;
    }

    public void addEvent(Event event) {
        auditService.writeAction("Add a new event", auditService.getTimestamp());

        if(checkLegalEvent(event)) {
            eventRepository.add(event);
            eventWritingService.writeEvent(event);
        } else {
            throw new IllegalArgumentException("\nCan't assign two shows in a room on the same date!  Didn't add the event!");
        }
    }

    public void addExistingEvent(Event event) {
        auditService.writeAction("Add an existing event from csv", auditService.getTimestamp());

        if(checkLegalEvent(event))
            eventRepository.add(event);
        else {
            throw new IllegalArgumentException("\nCan't assign two shows in a room on the same date!  Didn't add the event!");
        }
    }

    public void getExistingEvents() {
        auditService.writeAction("Get all existing events from csv", auditService.getTimestamp());

        EventReadingService eventReadingService = EventReadingService.getReadingInstance();
        ArrayList<Event> events = eventReadingService.readAllEvents();

        for(Event event : events) {
            addExistingEvent(event);
        }
    }

    public Event getEventById(Integer id) {
        auditService.writeAction("Get an event by id", auditService.getTimestamp());

        Event event = eventRepository.getEventById(id);

        if(event == null)
            throw new IllegalArgumentException("This id does not exist!");

        return event;
    }

    public ArrayList<Event> getAllEvents() {
        auditService.writeAction("Get all events", auditService.getTimestamp());
        return eventRepository.getAll();
    }

    public void changeEventShowById(Integer id, Show show) {
        auditService.writeAction("Change an event show by id", auditService.getTimestamp());

        Event event = getEventById(id);
        event.setShow(show);

        ArrayList<Event> events = getAllEvents();
        eventWritingService.updateEvents(events);
    }

    public void changeEventRoomById(Integer id, Room room) {
        auditService.writeAction("Change an event room by id", auditService.getTimestamp());

        Event event = getEventById(id);

        if(checkLegalEvent(new Event(event.getShow(), room, event.getDate()))) {
            event.setRoom(room);

            ArrayList<Event> events = getAllEvents();
            eventWritingService.updateEvents(events);
        } else {
            throw new IllegalArgumentException("\nCan't assign two shows in the a room on the same date! Didn't change event room!");
        }
    }

    public void changeEventDateById(Integer id, Date date) {
        auditService.writeAction("Change an event date by id", auditService.getTimestamp());

        Event event = getEventById(id);

        if(checkLegalEvent(new Event(event.getShow(), event.getRoom(), date))) {
            event.setDate(date);

            ArrayList<Event> events = getAllEvents();
            eventWritingService.updateEvents(events);
        } else {
            throw new IllegalArgumentException("\nCan't assign two shows in the a room on the same date! Didn't change event date!");
        }
    }

    public ArrayList<Event> getEventsByShowId(Integer id) {
        auditService.writeAction("Get events by show id", auditService.getTimestamp());
        return eventRepository.getAllByShowId(id);
    }

    public ArrayList<Event> getEventsByRoomId(Integer id) {
        auditService.writeAction("Get events by room id", auditService.getTimestamp());
        return eventRepository.getAllByRoomId(id);
    }

    public ArrayList<Event> getEventsByDate(Date date) {
        auditService.writeAction("Get events by date", auditService.getTimestamp());
        return eventRepository.getAllByDate(date);
    }
}
