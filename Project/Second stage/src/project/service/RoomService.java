package project.service;

import project.model.Room;
import project.repository.RoomRepository;
import project.service.storage.AuditService;
import project.service.storage.RoomReadingService;
import project.service.storage.RoomWritingService;

import java.util.ArrayList;

public class RoomService {
    private RoomRepository roomRepository = new RoomRepository();
    private static RoomService instance = new RoomService();
    RoomWritingService roomWritingService = RoomWritingService.getWritingInstance();
    AuditService auditService = AuditService.getInstance();

    private RoomService() { }

    public static RoomService getInstance() {
        return instance;
    }

    public void addRoom(Room room) {
        auditService.writeAction("Add a new room", auditService.getTimestamp());

        roomRepository.add(room);
        roomWritingService.writeRoom(room);
    }

    public void addExistingRoom(Room room) {
        auditService.writeAction("Add an existing room from csv", auditService.getTimestamp());
        roomRepository.add(room);
    }

    public void getExistingRooms() {
        auditService.writeAction("Get all existing rooms from csv", auditService.getTimestamp());

        RoomReadingService roomReadingService = RoomReadingService.getReadingInstance();
        ArrayList<Room> rooms = roomReadingService.readAllRooms();

        for(Room room : rooms) {
            addExistingRoom(room);
        }
    }

    public Room getRoomById(Integer id) {
        auditService.writeAction("Get a room by id", auditService.getTimestamp());

        Room room = roomRepository.getRoomById(id);

        if(room == null)
            throw new IllegalArgumentException("This id does not exist!");

        return room;
    }

    public ArrayList<Room> getRoomsByName(String name) {
        auditService.writeAction("Get rooms by name", auditService.getTimestamp());
        return roomRepository.getAllByName(name);
    }

    public ArrayList<Room> getAllRooms() {
        auditService.writeAction("Get all rooms", auditService.getTimestamp());
        return roomRepository.getAll();
    }

    public void changeRoomNameById(Integer id, String newName) {
        auditService.writeAction("Change a room name by id", auditService.getTimestamp());

        Room room = roomRepository.getRoomById(id);
        room.setRoomName(newName);

        ArrayList<Room> rooms = getAllRooms();
        roomWritingService.updateRooms(rooms);
    }

    public void changeRoomsName(String actualName, String newName) {
        auditService.writeAction("Change rooms name by actual name", auditService.getTimestamp());

        ArrayList<Room> rooms= roomRepository.getAllByName(actualName);

        for(Room room : rooms)
            room.setRoomName(newName);

        roomWritingService.updateRooms(rooms);
    }
}
