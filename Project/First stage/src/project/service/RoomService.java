package project.service;

import project.model.Room;
import project.repository.RoomRepository;

import java.util.ArrayList;

public class RoomService {
    private RoomRepository roomRepository = new RoomRepository();
    private static RoomService instance = new RoomService();

    private RoomService() { }

    public static RoomService getInstance() {
        return instance;
    }

    public void addRoom(Room room) {
        roomRepository.add(room);
    }

    public Room getRoomById(Integer id) {
        return roomRepository.getRoomById(id);
    }

    public ArrayList<Room> getRoomsByName(String name) {
        return roomRepository.getAllByName(name);
    }

    public ArrayList<Room> getAllRooms() {
        return roomRepository.getAll();
    }

    public void changeRoomNameById(Integer id, String newName) {
        Room room = roomRepository.getRoomById(id);

        if(room != null) {
            room.setRoomName(newName);
        } else {
            throw new IllegalArgumentException("This id does not exist!");
        }
    }

    public void changeRoomsName(String actualName, String newName) {
        ArrayList<Room> rooms= roomRepository.getAllByName(actualName);

        for(Room room : rooms)
            room.setRoomName(newName);
    }
}
