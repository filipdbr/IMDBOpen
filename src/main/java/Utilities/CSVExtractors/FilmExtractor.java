package Utilities.CSVExtractors;

import Entities.Business.Film.Film;
import com.opencsv.CSVReader;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FilmExtractor {

    public static List<Film> extractFilmsFromCSV(String filePath) {
        List<Film> films = new ArrayList<>();

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(filePath))
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())  // Set the separator to ';'
                .build()) {

            String[] header = reader.readNext(); // Read the header row
            String[] line;

            while ((line = reader.readNext()) != null) {
                try {
                    // Parse and create a Film instance from the CSV data
                    String imdb = line[0].isEmpty() ? "NA" : line[0];
                    String nom = line[1].isEmpty() ? "NA" : line[1];
                    String annee = line[2].isEmpty() ? "NA" : line[2];
                    String rating = line[3].isEmpty() ? "NA" : line[3];
                    String url = line[4].isEmpty() ? "NA" : line[4];
                    String lieuTour = line[5].isEmpty() ? "NA" : line[5];
                    String langue = line[6].isEmpty() ? "NA" : line[6];
                    String resume = line[7].isEmpty() ? "NA" : line[7];
                    String pays = line[8].isEmpty() ? "NA" : line[8];

                    // Create a new Film instance
                    Film film = new Film();
                    film.setImdb(imdb);
                    film.setNom(nom);
                    film.setAnnee(annee.equals("NA") ? Integer.parseInt(line [2]) : Integer.parseInt(annee));
                    film.setRating(rating.equals("NA") ? Double.parseDouble(line[3]) : Double.parseDouble(rating));
                    film.setUrl(url);
                    film.setLieuTour(lieuTour);
                    film.setLangue(langue);
                    film.setResume(resume);
                    film.setPays(pays);


                    films.add(film);
                } catch (Exception e) {
                    System.err.println("Error processing line: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
            e.printStackTrace();
        } catch (CsvValidationException e) {
            System.err.println("CSV validation error: " + e.getMessage());
            e.printStackTrace();
        }

        return films;
    }
}