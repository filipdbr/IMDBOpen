package Utilities.CSVExtractors;

import Entities.Business.Role.Role;
import Entities.Business.Film.Film;
import Entities.Business.Personne.Acteur;
import com.opencsv.CSVReader;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RoleExtractor {

    public static List<Role> extractRolesFromCSV(String filePath, List<Film> films, List<Acteur> actors) {
        List<Role> roles = new ArrayList<>();

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(filePath))
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build()) // Set the separator to ';'
                .build()) {

            String[] header = reader.readNext(); // Read the header row
            String[] line;

            while ((line = reader.readNext()) != null) {
                try {
                    // Parse and create a Role instance from the CSV data
                    String roleName = line[0];
                    String filmTitle = line[1];
                    String actorId = line[2]; // Use actorId instead of actorName to match type with Acteur's idImdb

                    // Find the corresponding Film and Acteur instances
                    Film film = films.stream().filter(f -> f.getNom().equals(filmTitle)).findFirst().orElse(null);
                    Acteur actor = actors.stream().filter(a -> a.getIdImdb() == Long.parseLong(actorId)).findFirst().orElse(null);

                    if (film != null && actor != null) {
                        // Create a new Role instance
                        Role role = new Role();
                        role.setRoleName(roleName);
                        role.setFilm(film);
                        role.setActor(actor);

                        roles.add(role);
                    } else {
                        System.err.println("Film or Actor not found for role: " + roleName);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing actor ID: " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("Unexpected error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
            e.printStackTrace();
        } catch (CsvValidationException e) {
            System.err.println("CSV validation error: " + e.getMessage());
            e.printStackTrace();
        }

        return roles;
    }
}
