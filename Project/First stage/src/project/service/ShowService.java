package project.service;

import project.model.Show;
import project.repository.ShowRepository;

import java.util.ArrayList;

public class ShowService {
    private ShowRepository showRepository = new ShowRepository();
    private static ShowService instance = new ShowService();

    private ShowService() { }

    public static ShowService getInstance() {
        return instance;
    }

    public void addShow(Show show) {
        showRepository.add(show);
    }

    public Show getShowById(Integer id) {
        return showRepository.getShowById(id);
    }

    public ArrayList<Show> getShowsByName(String name) {
        return showRepository.getAllByName(name);
    }

    public ArrayList<Show> getAllShows() {
        return showRepository.getAll();
    }

    public void changeShowNameById(Integer id, String newName) {
        Show show = showRepository.getShowById(id);

        if(show != null) {
            show.setShowName(newName);
        } else {
            throw new IllegalArgumentException("This id does not exist!");
        }
    }

    public void changeShowsName(String actualName, String newName) {
        ArrayList<Show> shows= showRepository.getAllByName(actualName);

        for(Show show : shows)
            show.setShowName(newName);
    }

    public void changeShowPriceById(Integer id, Double price) {
        Show show = showRepository.getShowById(id);

        if(show == null) {
            throw new IllegalArgumentException("This showId does not exist!");
        }

        show.setPrice(price);
    }
}
