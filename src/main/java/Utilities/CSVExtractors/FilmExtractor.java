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
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build()) // Set the separator to ';'
                .build()) {

            String[] header = reader.readNext(); // Read the header row
            String[] line;

            while ((line = reader.readNext()) != null) {
                try {
                    // Parse and create a Film instance from the CSV data
                    String nom = line[0];
                    String resume = line[1];
                    String pays = line[2];

                    // Create a new Film instance
                    Film film = new Film();
                    film.setNom(nom);        // Set the name from CSV
                    film.setResume(resume);  // Set the resume from CSV
                    film.setPays(pays);      // Set the country from CSV

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
