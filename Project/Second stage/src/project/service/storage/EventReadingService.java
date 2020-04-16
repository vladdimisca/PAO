package project.service.storage;

import project.model.*;
import project.service.RoomService;
import project.service.ShowService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class EventReadingService {
    private static EventReadingService instance = new EventReadingService();
    ShowService showService = ShowService.getInstance();
    RoomService roomService = RoomService.getInstance();

    private EventReadingService() {}

    public static EventReadingService getReadingInstance() {
        return instance;
    }

    public Path getPath() {
        return Paths.get("events.csv");
    }

    public ArrayList<Event> readAllEvents() {
        ArrayList<Event> events = new ArrayList<>();

        File file = new File(String.valueOf(getPath()));
        if(!file.exists())
            return events;

        try (BufferedReader csvReader = new BufferedReader(new FileReader(String.valueOf(getPath())))) {
            String line;
            while ((line = csvReader.readLine()) != null) {
                String[] data = line.split(",");

                events.add(new Event(parseInt(data[0]), showService.getShowById(parseInt(data[1])), roomService.getRoomById(parseInt(data[2])), data[3]));
            }
        } catch (IOException | ParseException e){
            e.printStackTrace();
        }
        return events;
    }
}
