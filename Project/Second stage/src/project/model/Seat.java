package project.model;

import java.io.Serializable;

public class Seat {
    private Integer row;
    private Integer column;

    public Seat(Integer row, Integer column) {
        this.row = row;
        this.column = column;
    }

    public Integer getRow() {
        return row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setSeat(Integer row, Integer column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }
}
