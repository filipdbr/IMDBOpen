package Utilities.CSVExtractors;

import Entities.Business.Personne.Acteur;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class ActorExtractor {

    public static List<Acteur> extractActorsFromCSV(String filePath) {
        List<Acteur> acteurs = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] header = reader.readNext(); // Read the header row
            String[] line;

            while ((line = reader.readNext()) != null) {
                try {
                    // Parse and create an Acteur instance from the CSV data
                    long idImdb = Long.parseLong(line[0]);
                    String identite = line[1];
                    String dateNaisStr = line[2];
                    String lieuNaissance = line[3];
                    double taille = Double.parseDouble(line[4]);
                    String url = line[5];

                    // Split Lieu Naissance (e.g., "Paris, France")
                    String[] lieuNaissanceParts = lieuNaissance.split(",");
                    String ville = lieuNaissanceParts[0].trim();
                    String pays = lieuNaissanceParts[lieuNaissanceParts.length - 1].trim();

                    // Split Identite (e.g., "Jean Dujardin")
                    String[] identiteParts = identite.split(" ");
                    String prenom = identiteParts[0];
                    String nom = identiteParts[identiteParts.length - 1];

                    // Parse dateNaissance to LocalDateTime
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDateTime dateNaissance = LocalDateTime.parse(dateNaisStr, formatter);

                    // Create a new Acteur instance
                    Acteur acteur = new Acteur();
                    acteur.setIdImdb(idImdb);
                    acteur.setNom(nom);
                    acteur.setPrenom(prenom);
                    acteur.setDateNaissance(dateNaissance);
                    acteur.setTaille(taille);

                    // Set creation and update dates
                    acteur.setCreatedDate(LocalDateTime.now());
                    acteur.setUpdatedDate(LocalDateTime.now());

                    acteurs.add(acteur);
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing numeric data from line: " + e.getMessage());
                } catch (DateTimeParseException e) {
                    System.err.println("Error parsing date from line: " + e.getMessage());
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("Error accessing array element: " + e.getMessage());
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

        return acteurs;
    }
}
