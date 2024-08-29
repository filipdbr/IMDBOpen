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

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class RoleExtractor {

    @Autowired
    private IFilmRepository filmRepository;

    @Autowired
    private IActeurRepository acteurRepository;

    @Autowired
    private IRoleRepository roleRepository;

    public List<Role> extractRolesFromCSV(String filePath, List<Film> films, List<Acteur> acteurs) {
        List<Role> roles = new ArrayList<>();

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(filePath))
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                .build()) {

            String[] header = reader.readNext(); // Read the header row
            String[] line;

            while ((line = reader.readNext()) != null) {
                try {
                    String filmId = line[0].isEmpty() ? null : line[0].trim();
                    String actorId = line[1].isEmpty() ? null : line[1].trim();
                    String roleName = line[2].isEmpty() ? null : line[2].trim();

                    Film film = films.stream()
                            .filter(f -> f.getId().equals(Long.parseLong(filmId)))
                            .findFirst()
                            .orElse(null);

                    Acteur acteur = acteurs.stream()
                            .filter(a -> a.getId().equals(Long.parseLong(actorId)))
                            .findFirst()
                            .orElse(null);

                    if (film != null && acteur != null) {
                        Role role = new Role();
                        role.setRoleName(roleName);
                        role.setFilm(film);
                        role.setActor(acteur);

                        roles.add(role);
                    }
                } catch (Exception e) {
                    System.err.println("Error processing line for role: " + (line.length > 1 ? line[2] : "Unknown") + " - " + e.getMessage());
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
