package project.service;

import project.model.Show;
import project.repository.ShowRepository;
import project.service.storage.AuditService;
import project.service.storage.ShowReadingService;
import project.service.storage.ShowWritingService;

import java.util.ArrayList;

public class ShowService {
    private ShowRepository showRepository = new ShowRepository();
    private static ShowService instance = new ShowService();
    ShowWritingService showWritingService = ShowWritingService.getWritingInstance();
    AuditService auditService = AuditService.getInstance();

    private ShowService() { }

    public static ShowService getInstance() {
        return instance;
    }

    public void addExistingShow(Show show) {
        auditService.writeAction("Add an existing show from csv", auditService.getTimestamp());
        showRepository.add(show);
    }

    public void getExistingShows() {
        auditService.writeAction("Get all existing shows from csv", auditService.getTimestamp());

        ShowReadingService showReadingService = ShowReadingService.getReadingInstance();
        ArrayList<Show> shows = showReadingService.readAllShows();

        for(Show show : shows) {
            addExistingShow(show);
        }
    }

    public void addShow(Show show) {
        auditService.writeAction("Add a new show", auditService.getTimestamp());

        showRepository.add(show);
        showWritingService.writeShow(show);
    }

    public Show getShowById(Integer id) {
        auditService.writeAction("Get a show by id", auditService.getTimestamp());

        Show show = showRepository.getShowById(id);

        if(show == null)
            throw new IllegalArgumentException("This id does not exist!");

        return show;
    }

    public ArrayList<Show> getShowsByName(String name) {
        auditService.writeAction("Get shows by name", auditService.getTimestamp());
        return showRepository.getAllByName(name);
    }

    public ArrayList<Show> getAllShows() {
        auditService.writeAction("Get all shows", auditService.getTimestamp());
        return showRepository.getAll();
    }

    public void changeShowNameById(Integer id, String newName) {
        auditService.writeAction("Change a show name by id", auditService.getTimestamp());

        Show show = showRepository.getShowById(id);
        show.setShowName(newName);

        ArrayList<Show> shows = getAllShows();
        showWritingService.updateShows(shows);
    }

    public void changeShowsName(String actualName, String newName) {
        auditService.writeAction("Change shows name by actual name", auditService.getTimestamp());

        ArrayList<Show> shows= showRepository.getAllByName(actualName);

        for(Show show : shows)
            show.setShowName(newName);

        showWritingService.updateShows(shows);
    }

    public void changeShowPriceById(Integer id, Double price) {
        auditService.writeAction("Change a show price by id", auditService.getTimestamp());

        Show show = showRepository.getShowById(id);
        show.setPrice(price);

        ArrayList<Show> shows = getAllShows();
        showWritingService.updateShows(shows);
    }
}
