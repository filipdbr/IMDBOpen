package Utilities.CSVExtractors;

import Entities.Business.Personne.Personne;
import Entities.Business.Personne.Acteur;
import Persistence.Repository.IActeurRepository;
import Persistence.Repository.IPersonneRepository;
import Service.ActeurService;
import com.opencsv.CSVReader;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

@Component
public class ActorExtractor {

    @Autowired
    private IActeurRepository acteurRepository;
    @Autowired
    private ActeurService acteurService;


    @Autowired
    private IPersonneRepository personneRepository;

    @Transactional
    public void extractActorsFromCSV(String filePath) {
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(filePath))
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                .build()) {

            // Read the header row
            reader.readNext();

            String[] line;
            while ((line = reader.readNext()) != null) {
                try {
                    // Parse CSV line
                    String identite = line[1].isEmpty() || "N/A".equals(line[1]) ? null : line[1].trim();
                    String dateNaissanceStr = line[2].isEmpty() || "N/A".equals(line[2]) ? null : line[2].trim();
                    String lieuNaissance = line[3].isEmpty() || "N/A".equals(line[3]) ? null : line[3].trim();
                    String imdbId = line[0].isEmpty() || "N/A".equals(line[0]) ? null : line[0].trim();
                    String tailleStr = line[4].isEmpty() || "N/A".equals(line[4]) ? null : line[4].trim();
                    String url = line[5].isEmpty() || "N/A".equals(line[5]) ? null : line[5].trim();

                    // Check if the Personne already exists
                    Optional<Personne> optionalPersonne = personneRepository.findByIdentiteAndDateNaissance(identite, dateNaissanceStr);

                    Personne personne;
                    if (optionalPersonne.isPresent()) {
                        // Personne exists, retrieve it
                        personne = optionalPersonne.get();
                    } else {
                        // Personne does not exist, create a new one
                        personne = new Personne();
                        personne.setIdentite(identite);
                        personne.setDateNaissance(dateNaissanceStr);
                        personne.setLieuNaissance(lieuNaissance);
                        personne.setUrl(url);

                        try {
                           acteurService.savePersonne(personne);
                           // Save the new Personne to the database
                        } catch (Exception e) {
                            System.err.println("Error saving Personne: " + identite + " - " + e.getMessage());
                            e.printStackTrace();
                            continue; // Skip to the next line
                        }
                    }

                    // Check if the Acteur already exists based on the Personne's ID
                    Optional<Acteur> optionalActeur = acteurRepository.findByPersonne(personne);

                    if (optionalActeur.isPresent()) {
                        // Acteur already exists, skip it
                        System.out.println("Acteur with Personne ID " + personne.getId() + " already exists. Skipping...");
                    } else {
                        // Acteur does not exist, create a new one
                        Acteur acteur = new Acteur();
                        acteur.setPersonne(personne); // Associate the Personne with the Acteur
                        acteur.setIdImdb(imdbId);
                        acteur.setTaille(tailleStr);

                        try {
                            acteurService.save(acteur);
                           // Save the new Acteur to the database
                        } catch (Exception e) {
                            System.err.println("Error saving Acteur: " + identite + " - " + e.getMessage());
                            e.printStackTrace();
                        }
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
    }
}
