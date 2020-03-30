package project.repository;

import project.model.Show;
import java.util.ArrayList;

public class ShowRepository {
    private ArrayList<Show> showDB;

    public ShowRepository() {
        showDB = new ArrayList<>();
    }

    public void add(Show show) {
        showDB.add(show);
    }

    public Show getShowById(Integer id) {
        for(Show show : showDB) {
            if(show.getShowId().equals(id)) {
                return show;
            }
        }
        return null;
    }

    public ArrayList<Show> getAll() {
        return showDB;
    }

    public ArrayList<Show> getAllByName(String name) {
        ArrayList<Show> shows = new ArrayList<>();

        for(Show show : showDB) {
            if(show.getShowName().equals(name)){
                shows.add(show);
            }
        }
        return shows;
    }
}
