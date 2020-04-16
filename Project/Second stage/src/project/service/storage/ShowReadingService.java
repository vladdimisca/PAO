package project.service.storage;

import project.model.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ShowReadingService {
    private static ShowReadingService instance = new ShowReadingService();

    private ShowReadingService() {}

    public static ShowReadingService getReadingInstance() {
        return instance;
    }

    public Path getPath() {
        return Paths.get("shows.csv");
    }

    public ArrayList<Show> readAllShows() {
        ArrayList<Show> shows = new ArrayList<>();

        File file = new File(String.valueOf(getPath()));
        if(!file.exists())
            return shows;

        try (BufferedReader csvReader = new BufferedReader(new FileReader(String.valueOf(getPath())))) {
            String line;
            while ((line = csvReader.readLine()) != null) {
                String[] data = line.split(",");

                shows.add(new Show(Integer.parseInt(data[0]), data[1], Double.parseDouble(data[2])));
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return shows;
    }
}
