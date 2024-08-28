package Utilities.CSVExtractors;

import Entities.Business.Role.Role;
import Entities.Business.Film.Film;
import Entities.Business.Personne.Acteur;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RoleExtractor {

    public static List<Role> extractRolesFromCSV(String filePath, List<Film> films, List<Acteur> actors) {
        List<Role> roles = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] header = reader.readNext(); // Read the header row
            String[] line;

            while ((line = reader.readNext()) != null) {
                try {
                    // Parse and create a Role instance from the CSV data
                    String roleName = line[0];
                    String filmTitle = line[1];
                    String actorName = line[2];

                    // Find the corresponding Film and Acteur instances
                    Film film = films.stream().filter(f -> f.getNom().equals(filmTitle)).findFirst().orElse(null);
                    Acteur actor = actors.stream().filter(a -> a.getIdImdb() == Long.parseLong(actorName)).findFirst().orElse(null);

                    if (film != null && actor != null) {
                        // Create a new Role instance
                        Role role = new Role();
                        role.setRoleName(roleName);
                        role.setFilm(film);
                        role.setActor(actor);

                        roles.add(role);
                    }
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

        return roles;
    }
}