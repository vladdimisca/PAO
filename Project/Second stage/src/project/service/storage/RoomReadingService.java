package project.service.storage;

import project.model.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class RoomReadingService {
    private static RoomReadingService instance = new RoomReadingService();

    private RoomReadingService() {}

    public static RoomReadingService getReadingInstance() {
        return instance;
    }

    public Path getPath() {
        return Paths.get("rooms.csv");
    }

    public ArrayList<Room> readAllRooms() {
        ArrayList<Room> rooms = new ArrayList<>();

        File file = new File(String.valueOf(getPath()));
        if(!file.exists())
            return rooms;

        try (BufferedReader csvReader = new BufferedReader(new FileReader(String.valueOf(getPath())))) {
            String line;
            while ((line = csvReader.readLine()) != null) {
                String[] data = line.split(",");

                rooms.add(new Room(Integer.parseInt(data[0]), data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3])));
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return rooms;
    }
}
