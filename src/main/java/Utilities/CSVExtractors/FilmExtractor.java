package Utilities.CSVExtractors;

import Entities.Business.Film.Film;
import Entities.Business.Pays.Pays;
import Persistence.Repository.IFilmRepository;
import Service.PaysService;
import com.opencsv.CSVReader;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class FilmExtractor {

    @Autowired
    private IFilmRepository filmRepository;

    @Autowired
    private PaysService paysService;

    public List<Film> extractFilmsFromCSV(String filePath) {
        List<Film> films = new ArrayList<>();

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(filePath))
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())  // Set the separator to ';'
                .build()) {

            String[] header = reader.readNext(); // Read the header row
            String[] line;

            while ((line = reader.readNext()) != null) {
                try {
                    // Parse and create a Film instance from the CSV data
                    String imdb = line[0].isEmpty() ? null : line[0].trim();
                    String nom = line[1].isEmpty() ? null : line[1].trim();
                    String annee = line[2].isEmpty() ? null : line[2].trim();
                    String rating = line[3].isEmpty() ? null : line[3].trim();
                    String url = line[4].isEmpty() ? null : line[4].trim();
                    String lieuTour = line[5].isEmpty() ? null : line[5].trim();
                    String langue = line[7].isEmpty() ? null : line[7].trim();
                    String resume = line[8].isEmpty() ? null : line[8].trim();
                    String paysName = line[9].isEmpty() ? null : line[9].trim();

                    // Check the length of the resume to avoid Data Truncation errors
                    if (resume != null && resume.length() > 10000) {
                        System.err.println("Resume too long for film: " + nom + ". Truncating to 10,000 characters.");
                        resume = resume.substring(0, 10000);  // Truncate to max length
                    }

                    // Create or find a Pays instance using PaysService
                    Pays pays = paysService.findOrCreatePays(paysName);

                    // Create a new Film instance
                    Film film = new Film();
                    film.setImdb(imdb);
                    film.setNom(nom);
                    film.setAnnee(annee);
                    film.setRating(rating);
                    film.setUrl(url);
                    film.setLieuTour(lieuTour);
                    film.setLangue(langue);
                    film.setResume(resume);
                    film.setPays(pays.getName());  // Correctly set the Pays object

                    films.add(film);
                } catch (Exception e) {
                    System.err.println("Error processing line for film: " + (line.length > 1 ? line[1] : "Unknown") + " - " + e.getMessage());
                    e.printStackTrace();  // Print full stack trace for debugging
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

    public void saveFilms(List<Film> films) {
        filmRepository.saveAll(films);
    }
}