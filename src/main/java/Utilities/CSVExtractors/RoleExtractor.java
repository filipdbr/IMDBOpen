package Utilities.CSVExtractors;

import Entities.Business.Film.Film;
import Entities.Business.Personne.Acteur;
import Entities.Business.Role.Role;
import Persistence.Repository.IRoleRepository;
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
public class RoleExtractor {

    @Autowired
    private IRoleRepository roleRepository;

    public List<Role> extractRolesFromCSV(String filePath, List<Film> films, List<Acteur> actors) {
        List<Role> roles = new ArrayList<>();

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(filePath))
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                .build()) {

            String[] header = reader.readNext(); // Read the header row
            String[] line;

            while ((line = reader.readNext()) != null) {
                try {
                    String roleName = line[0].isEmpty() ? null : line[0].trim();
                    String actorImdb = line[1].isEmpty() ? null : line[1].trim();
                    String filmImdb = line[2].isEmpty() ? null : line[2].trim();

                    Role role = new Role();
                    role.setRoleName(roleName);
                    role.setActorImdb(actorImdb);
                    role.setFilmImdb(filmImdb);

                    roles.add(role);
                } catch (Exception e) {
                    System.err.println("Error processing line for role: " + (line.length > 0 ? line[0] : "Unknown") + " - " + e.getMessage());
                    e.printStackTrace();
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