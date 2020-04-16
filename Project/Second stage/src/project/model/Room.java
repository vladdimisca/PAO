package project.model;

import java.util.Arrays;
import java.util.Objects;

import static java.lang.Integer.max;

public class Room {
    private static Integer id = 0;
    private String roomName;
    private final Integer roomId;
    private Integer hallRows;
    private Integer hallCols;
    private Boolean[][] hall = null;

    public Room(String roomName, Integer rows, Integer columns) {
        this.roomName = roomName;
        this.hallRows = rows;
        this.hallCols = columns;
        this.roomId = ++id;
    }

    public Room(Integer roomId, String roomName, Integer rows, Integer columns) {
        this.roomName = roomName;
        this.hallRows = rows;
        this.hallCols = columns;
        this.roomId = roomId;
        id = max(roomId, id);
    }

    public Room(Room room) {
        this.roomName = room.roomName;
        this.roomId = room.roomId;
        this.hallRows = room.hallRows;
        this.hallCols = room.hallCols;
        this.hall = new Boolean[hallRows][hallCols];

        for(int r = 0; r < hallRows; r++)
            for(int c = 0; c < hallCols; c++)
                hall[r][c] = false;
    }

    public String getRoomName() {
        return roomName;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public Integer getHallCols() {
        return hallCols;
    }

    public Integer getHallRows() {
        return hallRows;
    }

    public Boolean checkHallSeat(Integer row, Integer column) {
        return hall[row][column];
    }

    public Seat getFreeSeat() {
        for(int r = 0; r < hallRows; r++)
            for(int c = 0; c < hallCols; c++) {
                if (!hall[r][c]) {
                    hall[r][c] = true;
                    Seat seat = new Seat(r, c);
                    return seat;
                }
            }
        return null;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setHallRows(Integer rows) {
        this.hallRows = rows;
    }

    public void setHallCols(Integer rows) {
        this.hallCols = rows;
    }

    public void setHallSeat(Integer row, Integer column, Boolean type) {
        this.hall[row][column] = type;
    }

    @Override
    public boolean equals(Object o) {
        Room room = (Room) o;
        return this.roomId == room.roomId;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(roomName, roomId, hallRows, hallCols);
        result = 31 * result + Arrays.hashCode(hall);
        return result;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomName='" + roomName + '\'' +
                ", roomId=" + roomId +
                ", hallRows=" + hallRows +
                ", hallCols=" + hallCols +
                '}';
    }
}
