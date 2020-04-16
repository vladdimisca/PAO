package project.service.storage;

import project.model.Show;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ShowWritingService {
    private static ShowWritingService instance = new ShowWritingService();

    private ShowWritingService() {}

    public static ShowWritingService getWritingInstance() {
        return instance;
    }

    public Path getPath() {
        return Paths.get("shows.csv");
    }

    public void writeShow(Show show) {
        try (FileWriter csvWriter = new FileWriter(String.valueOf(getPath()), true)) {
            List<String> line =  Arrays.asList(Integer.toString(show.getShowId()), show.getShowName(), Double.toString(show.getPrice()));
            csvWriter.append(String.join(",", line));
            csvWriter.append("\n");
            csvWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateShows(ArrayList<Show> shows) {
        try (FileWriter csvWriter = new FileWriter(String.valueOf(getPath()), false)) {
            for(Show show : shows) {
                List<String> line = Arrays.asList(Integer.toString(show.getShowId()), show.getShowName(), Double.toString(show.getPrice()));
                csvWriter.append(String.join(",", line));
                csvWriter.append("\n");
                csvWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
