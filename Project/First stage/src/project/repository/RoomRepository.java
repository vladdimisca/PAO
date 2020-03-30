package project.repository;

import project.model.Room;

import java.util.ArrayList;

public class RoomRepository {
    private ArrayList<Room> roomDB;

    public RoomRepository() {
        roomDB = new ArrayList<>();
    }

    public void add(Room room) {
        roomDB.add(room);
    }

    public Room getRoomById(Integer id) {
        for(Room room : roomDB) {
            if(room.getRoomId().equals(id)) {
                return room;
            }
        }
        return null;
    }

    public ArrayList<Room> getAll() {
        return roomDB;
    }

    public ArrayList<Room> getAllByName(String name) {
        ArrayList<Room> rooms = new ArrayList<>();

        for(Room room : roomDB) {
            if(room.getRoomName().equals(name)){
                rooms.add(room);
            }
        }
        return rooms;
    }
}
