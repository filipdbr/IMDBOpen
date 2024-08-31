package Utilities.CSVExtractors;

import Entities.Business.Film.Film;
import Entities.Business.Personne.Acteur;
import Entities.Business.Role.Role;
import Persistence.Repository.IActeurRepository;
import Persistence.Repository.IFilmRepository;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class RoleExtractor {

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private IFilmRepository filmRepository;

    @Autowired
    private IActeurRepository acteurRepository;

    private static final int BATCH_SIZE = 500; // Adjust batch size as needed

    @Transactional
    public void extractRolesFromCSV(String filePath) {
        List<Role> rolesToSave = new ArrayList<>();
        Set<String> uniqueRoleIdentifiers = new HashSet<>();

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(filePath))
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                .build()) {

            // Read the header row
            reader.readNext();

            String[] line;
            while ((line = reader.readNext()) != null) {
                String filmId = line[0].isEmpty() ? null : line[0].trim();
                String acteurId = line[1].isEmpty() ? null : line[1].trim();
                String roleName = line[2].isEmpty() ? null : line[2].trim();

                // Create a unique identifier for the role
                String roleIdentifier = filmId + "-" + acteurId + "-" + roleName;

                // Check if the Role already exists
                if (uniqueRoleIdentifiers.add(roleIdentifier)) {
                    // Check if the Role already exists in the database
                    if (roleRepository.existsByFilmIdAndActeurIdAndRoleName(filmId, acteurId, roleName)) {
                        System.out.println("Role already exists - Film ID: " + filmId + ", Actor ID: " + acteurId + ", Role Name: " + roleName);
                    } else {
                        // Create a new Role entity
                        Role role = new Role();
                        role.setRoleName(roleName);
                        role.setFilmId(filmId); // Set filmId directly
                        role.setActeurId(acteurId); // Set acteurId directly

                        rolesToSave.add(role);

                        // Process batch every BATCH_SIZE entries or so
                        if (rolesToSave.size() >= BATCH_SIZE) {
                            roleRepository.saveAll(rolesToSave);
                            roleRepository.flush();
                            rolesToSave.clear();
                        }
                    }
                }
            }

            // Save any remaining roles in the batch
            if (!rolesToSave.isEmpty()) {
                roleRepository.saveAll(rolesToSave);
                roleRepository.flush();
            }

        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
            e.printStackTrace();
        } catch (CsvValidationException e) {
            System.err.println("CSV validation error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
