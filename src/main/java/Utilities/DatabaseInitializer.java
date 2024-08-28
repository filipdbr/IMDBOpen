package Utilities;

import Entities.Business.Film.Film;
import Entities.Business.Personne.Acteur;
import Entities.Business.Role.Role;
import Persistence.Repository.IActeurRepository;
import Persistence.Repository.IFilmRepository;
import Persistence.Repository.IRoleRepository;
import Utilities.CSVExtractors.ActorExtractor;
import Utilities.CSVExtractors.FilmExtractor;
import Utilities.CSVExtractors.RoleExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Component
public class DatabaseInitializer {

    private static final String DB_URL = "jdbc:h2:mem:testimdb";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    @Autowired
    private IActeurRepository iActeurRepository;

    @Autowired
    private IFilmRepository iFilmRepository;

    @Autowired
    private IRoleRepository iRoleRepository;

    public void createDatabase() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            Statement statement = connection.createStatement();
            String sql = "CREATE SCHEMA IF NOT EXISTS testimdb";
            statement.executeUpdate(sql);
            System.out.println("Database created or already exists.");
            populateDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populateDatabase() {
        List<Acteur> acteurs = ActorExtractor.extractActorsFromCSV("src/main/resources/data/actors.csv");
        List<Film> films = FilmExtractor.extractFilmsFromCSV("src/main/resources/data/films.csv");
        List<Role> roles = RoleExtractor.extractRolesFromCSV("src/main/resources/data/roles.csv", films, acteurs);

        iActeurRepository.saveAll(acteurs);
        iFilmRepository.saveAll(films);
        iRoleRepository.saveAll(roles);
    }
}