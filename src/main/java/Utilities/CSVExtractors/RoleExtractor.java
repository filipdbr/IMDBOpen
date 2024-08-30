package Utilities.CSVExtractors;

import Entities.Business.Film.Film;
import Entities.Business.Personne.Acteur;
import Entities.Business.Role.Role;
import Persistence.Repository.IFilmRepository;
import Persistence.Repository.IActeurRepository;
import Persistence.Repository.IRoleRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class RoleExtractor {

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private IFilmRepository filmRepository;

    @Autowired
    private IActeurRepository acteurRepository;

    @Transactional // Ensure each save operation is handled in its own transaction
    public List<Role> extractRolesFromCSV(String filePath) {
        List<Role> roles = new ArrayList<>();

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(filePath))
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                .build()) {

            String[] header = reader.readNext(); // Read the header row
            String[] line;

            while ((line = reader.readNext()) != null) {
                String filmId = line[0].isEmpty() ? null : line[0].trim();
                String acteurId = line[1].isEmpty() ? null : line[1].trim();
                String roleName = line[2].isEmpty() ? null : line[2].trim();

                // Check if Film and Acteur exist
                Film film = filmRepository.findByImdb(filmId);
                Acteur acteur = acteurRepository.findByImdb(acteurId);

                if (film != null && acteur != null) {
                    Role role = new Role();
                    role.setRoleName(roleName);
                    role.setFilmId(filmId);
                    role.setActeurId(acteurId);

                    try {
                        roleRepository.save(role);
                        roles.add(role);
                    } catch (Exception e) {
                        System.err.println("Error saving role: " + roleName + " - " + e.getMessage());
                        e.printStackTrace();
                    }
                } else {
                    System.err.println("Error: Film or Acteur not found for Role - Film ID: " + filmId + ", Acteur ID: " + acteurId);
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
