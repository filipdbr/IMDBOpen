package Utilities.CSVExtractors;

import Entities.Business.Personne.Personne;
import Entities.Business.Personne.Acteur;
import Persistence.Repository.IActeurRepository;
import Persistence.Repository.IPersonneRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class ActorExtractor {

    @Autowired
    private IActeurRepository acteurRepository;

    @Autowired
    private IPersonneRepository personneRepository;

    public List<Personne> extractPersonsFromCSV(String filePath) {
        List<Personne> personnes = new ArrayList<>();

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(filePath))
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                .build()) {

            String[] header = reader.readNext(); // Read the header row
            String[] line;

            while ((line = reader.readNext()) != null) {
                try {
                    String nom = line[0].isEmpty() ? null : line[0].trim();
                    String prenom = line[1].isEmpty() ? null : line[1].trim();
                    String dateNaissance = line[2].isEmpty() ? null : line[2].trim();
                    String lieuNaissance = line[3].isEmpty() ? null : line[3].trim();

                    Personne personne = new Personne();
                    personne.setNom(nom);
                    personne.setPrenom(prenom);
                    personne.setDateNaissance(LocalDate.parse(dateNaissance)); // Assuming proper date parsing
                    personne.setLieuNaissance(lieuNaissance);

                    personnes.add(personne);
                } catch (Exception e) {
                    System.err.println("Error processing line for person: " + (line.length > 1 ? line[0] : "Unknown") + " - " + e.getMessage());
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

        return personnes;
    }

    public List<Acteur> extractActorsFromCSV(String filePath, List<Personne> personnes) {
        List<Acteur> acteurs = new ArrayList<>();

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(filePath))
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                .build()) {

            String[] header = reader.readNext(); // Read the header row
            String[] line;

            while ((line = reader.readNext()) != null) {
                try {
                    String personneId = line[0].isEmpty() ? null : line[0].trim();
                    String imdbId = line[1].isEmpty() ? null : line[1].trim();
                    String taille = line[2].isEmpty() ? null : line[2].trim();

                    Personne personne = personnes.stream()
                            .filter(p -> p.getId().equals(Long.parseLong(personneId)))
                            .findFirst()
                            .orElse(null);

                    if (personne != null) {
                        Acteur acteur = new Acteur();
                        acteur.setPersonne(personne);
                        acteur.setIdImdb(imdbId);
                        acteur.setTaille(Double.parseDouble(taille));

                        acteurs.add(acteur);
                    }
                } catch (Exception e) {
                    System.err.println("Error processing line for actor: " + (line.length > 1 ? line[1] : "Unknown") + " - " + e.getMessage());
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

        return acteurs;
    }
}
