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
import java.sql.DatabaseMetaData;
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
            createTablesIfNotExist(connection);
            populateDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTablesIfNotExist(Connection connection) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();

        // Check and create Film table
        if (!tableExists(metaData, "FILM")) {
            String createFilmTable = "CREATE TABLE FILM (id VARCHAR(255) PRIMARY KEY, nom VARCHAR(255), annee INT, rating DOUBLE, url VARCHAR(255), lieuTour VARCHAR(255), langue VARCHAR(255), resume VARCHAR(255), pays VARCHAR(255))";
            connection.createStatement().executeUpdate(createFilmTable);
            System.out.println("Film table created.");
        }

        // Check and create Acteur table
        if (!tableExists(metaData, "ACTEUR")) {
            String createActeurTable = "CREATE TABLE ACTEUR (id VARCHAR(255) PRIMARY KEY, identite VARCHAR(255), dateNaissance TIMESTAMP)";
            connection.createStatement().executeUpdate(createActeurTable);
            System.out.println("Acteur table created.");
        }

        // Check and create Role table
        if (!tableExists(metaData, "ROLE")) {
            String createRoleTable = "CREATE TABLE ROLE (id VARCHAR(255) PRIMARY KEY, roleName VARCHAR(255), filmId VARCHAR(255), actorId VARCHAR(255), FOREIGN KEY (filmId) REFERENCES FILM(id), FOREIGN KEY (actorId) REFERENCES ACTEUR(id))";
            connection.createStatement().executeUpdate(createRoleTable);
            System.out.println("Role table created.");
        }
    }

    private boolean tableExists(DatabaseMetaData metaData, String tableName) throws SQLException {
        try (var rs = metaData.getTables(null, null, tableName.toUpperCase(), null)) {
            return rs.next();
        }
    }

    private void populateDatabase() {
        // Populate Film table
        List<Film> films = FilmExtractor.extractFilmsFromCSV("src/main/resources/CSV/films.csv");
        iFilmRepository.saveAll(films);

        // Populate Acteur table
        List<Acteur> acteurs = ActorExtractor.extractActorsFromCSV("src/main/resources/CSV/acteurs.csv");
        iActeurRepository.saveAll(acteurs);

        // Populate Role table
        List<Role> roles = RoleExtractor.extractRolesFromCSV("src/main/resources/CSV/roles.csv", films, acteurs);
        iRoleRepository.saveAll(roles);
    }
}