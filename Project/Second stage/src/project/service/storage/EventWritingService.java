package project.service.storage;

import project.model.Event;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class EventWritingService {
    private static EventWritingService instance = new EventWritingService();

    private EventWritingService() {}

    public static EventWritingService getWritingInstance() {
        return instance;
    }

    public Path getPath() {
        return Paths.get("events.csv");
    }

    public void writeEvent(Event event) {
        try (FileWriter csvWriter = new FileWriter(String.valueOf(getPath()), true)) {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            List<String> line =  Arrays.asList(Integer.toString(event.getEventId()), Integer.toString(event.getShow().getShowId()),
                                               Integer.toString(event.getRoom().getRoomId()), dateFormat.format(event.getDate()));
            csvWriter.append(String.join(",", line));
            csvWriter.append("\n");
            csvWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateEvents(ArrayList<Event> events) {
        try (FileWriter csvWriter = new FileWriter(String.valueOf(getPath()), false)) {
            for(Event event : events) {
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                List<String> line = Arrays.asList(Integer.toString(event.getEventId()), Integer.toString(event.getShow().getShowId()),
                                                  Integer.toString(event.getRoom().getRoomId()), dateFormat.format(event.getDate()));
                csvWriter.append(String.join(",", line));
                csvWriter.append("\n");
                csvWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
