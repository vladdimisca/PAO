package project.service.storage;

import project.model.Room;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RoomWritingService {
    private static RoomWritingService instance = new RoomWritingService();

    private RoomWritingService() {}

    public static RoomWritingService getWritingInstance() {
        return instance;
    }

    public Path getPath() {
        return Paths.get("rooms.csv");
    }

    public void writeRoom(Room room) {
        try (FileWriter csvWriter = new FileWriter(String.valueOf(getPath()), true)) {
            List<String> line =  Arrays.asList(Integer.toString(room.getRoomId()), room.getRoomName(), Integer.toString(room.getHallRows()),Integer.toString(room.getHallCols()));
            csvWriter.append(String.join(",", line));
            csvWriter.append("\n");
            csvWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateRooms(ArrayList<Room> rooms) {
        try (FileWriter csvWriter = new FileWriter(String.valueOf(getPath()), false)) {
            for(Room room : rooms) {
                List<String> line = Arrays.asList(Integer.toString(room.getRoomId()), room.getRoomName(), Integer.toString(room.getHallRows()),Integer.toString(room.getHallCols()));
                csvWriter.append(String.join(",", line));
                csvWriter.append("\n");
                csvWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
