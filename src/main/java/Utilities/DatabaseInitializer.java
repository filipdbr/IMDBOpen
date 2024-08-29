package Utilities;

import Entities.Business.Film.Film;
import Entities.Business.Personne.Acteur;
import Entities.Business.Personne.Personne;
import Entities.Business.Role.Role;
import Persistence.Repository.IActeurRepository;
import Persistence.Repository.IFilmRepository;
import Persistence.Repository.IPersonneRepository;
import Persistence.Repository.IRoleRepository;
import Utilities.CSVExtractors.ActorExtractor;
import Utilities.CSVExtractors.FilmExtractor;
import Utilities.CSVExtractors.RoleExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Component
public class DatabaseInitializer {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private IActeurRepository iActeurRepository;

    @Autowired
    private IFilmRepository iFilmRepository;

    @Autowired
    private IRoleRepository iRoleRepository;

    @Autowired
    private IPersonneRepository iPersonneRepository;

    public void createDatabase() {
        try (Connection connection = dataSource.getConnection()) {
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
        if (!tableExists(metaData, "film")) {
            String createFilmTable = "CREATE TABLE film (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "annee VARCHAR(10) NOT NULL, " +
                    "langue VARCHAR(100) NOT NULL, " +
                    "rating VARCHAR(4) NOT NULL, " +
                    "resume VARCHAR(10001) NOT NULL, " +
                    "url VARCHAR(500))";
            connection.createStatement().executeUpdate(createFilmTable);
            System.out.println("Film table created.");
        }

        // Check and create Personne table
        if (!tableExists(metaData, "personne")) {
            String createPersonneTable = "CREATE TABLE personne (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "nom VARCHAR(255) NOT NULL, " +
                    "prenom VARCHAR(255), " +
                    "date_naissance DATETIME, " +
                    "lieu_naissance VARCHAR(255))";
            connection.createStatement().executeUpdate(createPersonneTable);
            System.out.println("Personne table created.");
        }

        // Check and create Acteur table
        if (!tableExists(metaData, "acteur")) {
            String createActeurTable = "CREATE TABLE acteur (" +
                    "id_acteur BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "personne_id BIGINT, " +
                    "id_imdb VARCHAR(255), " +
                    "taille DOUBLE, " +
                    "FOREIGN KEY (personne_id) REFERENCES personne(id))";
            connection.createStatement().executeUpdate(createActeurTable);
            System.out.println("Acteur table created.");
        }

        // Check and create Role table
        if (!tableExists(metaData, "role")) {
            String createRoleTable = "CREATE TABLE role (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "role_name VARCHAR(255) NOT NULL, " +
                    "film_id BIGINT NOT NULL, " +
                    "actor_id BIGINT NOT NULL, " +
                    "FOREIGN KEY (film_id) REFERENCES film(id), " +
                    "FOREIGN KEY (actor_id) REFERENCES acteur(id_acteur))";
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

        // Populate Personne and Acteur tables
        List<Personne> personnes = ActorExtractor.extractPersonsFromCSV("src/main/resources/CSV/acteurs.csv");
        iPersonneRepository.saveAll(personnes);

        List<Acteur> acteurs = ActorExtractor.extractActorsFromCSV("src/main/resources/CSV/acteurs.csv", personnes);
        iActeurRepository.saveAll(acteurs);

        // Populate Role table
        List<Role> roles = RoleExtractor.extractRolesFromCSV("src/main/resources/CSV/roles.csv", films, acteurs);
        iRoleRepository.saveAll(roles);
    }
}