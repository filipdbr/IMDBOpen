package Utilities.CSVExtractors;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.opencsv.CSVReader;
import Entities.Business.Film.Genre;
import Entities.Business.Pays;
import Service.FilmService;
import Web.Model.DTO.FilmDTO;
import com.opencsv.exceptions.CsvValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilmExtractor {

    private static final Logger logger = LoggerFactory.getLogger(FilmExtractor.class);

    private FilmService filmService;

    public FilmExtractor(FilmService filmService) {
        this.filmService = filmService;
    }

    public void parseFilmsFromCSV(String filePath) throws IOException {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            // Skip header row if present
            reader.readNext();

            String[] line;
            while ((line = reader.readNext()) != null) {
                try {
                    // Extract film data
                    String imdb = line[0]; // Adjust index as per your CSV
                    String nom = line[1];  // Adjust index as per your CSV
                    int annee = Integer.parseInt(line[2]); // Adjust index as per your CSV
                    double rating = Double.parseDouble(line[3]); // Adjust index as per your CSV
                    String url = line[4]; // Adjust index as per your CSV
                    String lieuTour = line[5]; // Adjust index as per your CSV
                    String langue = line[6]; // Adjust index as per your CSV
                    String resume = line[7]; // Adjust index as per your CSV
                    String pays = line[8]; // Adjust index as per your CSV

                    FilmDTO film = new FilmDTO();
                    film.setImdb(imdb);
                    film.setNom(nom);
                    film.setAnnee(annee);
                    film.setRating(rating);
                    film.setUrl(url);
                    film.setLieuTour(lieuTour);
                    film.setLangue(langue);
                    film.setResume(resume);
                    film.setPays(pays);

                    // Parse genres and countries
                    parseGenresAndCountries(film, line);

                    // Save the film
                    filmService.createFilm(film);
                } catch (Exception e) {
                    logger.error("Error processing film entry: {}. Reason: {}", line, e.getMessage());
                }
            }
        } catch (IOException e) {
            logger.error("Error reading CSV file. Reason: {}", e.getMessage());
            throw e; // Rethrow exception to handle it outside
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

    private void parseGenresAndCountries(FilmDTO film, String[] line) {
        try {
            List<String> genreNames = extractGenres(line); // Implement this method
            List<String> countryNames = extractCountries(line); // Implement this method

            for (String genreName : genreNames) {
                Genre genre = filmService.findOrCreateGenre(genreName);
                film.getGenres().add(genre);
            }

            for (String countryName : countryNames) {
                Pays pays = filmService.findOrCreatePays(countryName);
                film.setPays(pays.getName());
            }
        } catch (Exception e) {
            logger.error("Error parsing genres or countries for film. Reason: {}", e.getMessage());
        }
    }

    // Implement methods to extract genres and countries from CSV line
    private List<String> extractGenres(String[] line) {
        // Placeholder implementation; adjust according to your CSV format
        return List.of(line[9].split(",")); // Adjust index and parsing logic
    }

    private List<String> extractCountries(String[] line) {
        // Placeholder implementation; adjust according to your CSV format
        return List.of(line[10].split(",")); // Adjust index and parsing logic
    }
}