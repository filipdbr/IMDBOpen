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
import java.util.Optional;

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

                // Use Optional to handle the potential absence of Film and Acteur
                Optional<Film> optionalFilm = filmRepository.findByImdb(filmId);
                Optional<Acteur> optionalActeur = acteurRepository.findByImdb(acteurId);

                if (optionalFilm.isPresent() && optionalActeur.isPresent()) {
                    Role role = new Role();
                    role.setRoleName(roleName);
                    role.setFilmId(filmId); // Set filmId directly
                    role.setActeurId(acteurId); // Set acteurId directly

                    // Check for duplicates before saving
                    Optional<Role> existingRole = roleRepository.findRoleByFilmIdAndActeurIdAndRoleName(filmId, acteurId, roleName);
                    if (existingRole.isEmpty()) {
                        try {
                            roleRepository.save(role);
                            roles.add(role);
                        } catch (Exception e) {
                            System.err.println("Error saving role: " + roleName + " - " + e.getMessage());
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("Role already exists - Film ID: " + filmId + ", Actor ID: " + acteurId + ", Role Name: " + roleName);
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
