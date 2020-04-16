package project.model;

import static sun.swing.MenuItemLayoutHelper.max;

public class Show {
    private static Integer id = 0;
    private String showName;
    private Integer showId;
    private Double price;

    public Show(String showName, Double price) {
        this.price = price;
        this.showName = showName;
        this.showId = ++id;
    }

    public Show(Integer showId, String showName, Double price) {
        this.price = price;
        this.showName = showName;
        this.showId = showId;
        id = max(showId, id);
    }

    public Double getPrice() {
        return price;
    }

    public String getShowName() {
        return showName;
    }

    public Integer getShowId() {
        return showId;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    @Override
    public String toString() {
        return "Show{" +
                "showName='" + showName + '\'' +
                ", showId=" + showId +
                ", price=" + price +
                '}';
    }
}
