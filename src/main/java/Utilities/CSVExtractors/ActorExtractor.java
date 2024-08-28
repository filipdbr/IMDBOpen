package Utilities.CSVExtractors;

import Entities.Business.Personne.Acteur;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ActorExtractor {

    public static List<Acteur> extractActorsFromCSV(String filePath) {
        List<Acteur> actors = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] header = reader.readNext(); // Read the header row
            String[] line;

            while ((line = reader.readNext()) != null) {
                try {
                    // Parse and create an Acteur instance from the CSV data
                    String identite = line[0];
                    String dateNaissance = line[1];

                    // Create a new Acteur instance
                    Acteur acteur = new Acteur();
                    acteur.setIdImdb(Long.parseLong(identite));
                    acteur.setDateNaissance(LocalDateTime.parse(dateNaissance));

                    actors.add(acteur);
                } catch (Exception e) {
                    System.err.println("Unexpected error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }

        return actors;
    }
}
