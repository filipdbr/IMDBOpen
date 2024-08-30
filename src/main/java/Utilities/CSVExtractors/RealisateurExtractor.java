package Utilities.CSVExtractors;

import Entities.Business.Personne.Personne;
import Entities.Business.Personne.Realisateur;
import Persistence.Repository.IRealisateurRepository;
import Persistence.Repository.IPersonneRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@Component
public class RealisateurExtractor {

    @Autowired
    private IRealisateurRepository realisateurRepository;

    @Autowired
    private IPersonneRepository personneRepository;

    private static final DateTimeFormatter[] DATE_FORMATTERS = {
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("MM/dd/yyyy"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("MMMM d yyyy"),  // For "February 19 1940"
            DateTimeFormatter.ofPattern("d MMMM yyyy"),  // For "19 February 1940"
            DateTimeFormatter.ofPattern("yyyy/MM/dd"),  // Additional patterns if needed
            DateTimeFormatter.ofPattern("d MMM yyyy")    // For formats like "19 Feb 1940"
    };

    @Transactional // Ensure each save operation is handled in its own transaction
    public void extractRealisateursFromCSV(String filePath) {
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(filePath))
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                .build()) {

            String[] header = reader.readNext(); // Read the header row
            String[] line;

            while ((line = reader.readNext()) != null) {
                try {
                    // Parse CSV line
                    String nom = line[0].isEmpty() || "N/A".equals(line[0]) ? null : line[0].trim();
                    String prenom = line[1].isEmpty() || "N/A".equals(line[1]) ? null : line[1].trim();
                    String dateNaissanceStr = line[2].isEmpty() || "N/A".equals(line[2]) ? null : line[2].trim();
                    String lieuNaissance = line[3].isEmpty() || "N/A".equals(line[3]) ? null : line[3].trim();
                    String imdbId = line[4].isEmpty() || "N/A".equals(line[4]) ? null : line[4].trim();

                    LocalDate dateNaissance = parseDate(dateNaissanceStr);

                    // Check if the Personne already exists
                    Optional<Personne> optionalPersonne = personneRepository.findByNomAndPrenomAndDateNaissance(nom, prenom, dateNaissance);

                    Personne personne;
                    if (optionalPersonne.isPresent()) {
                        // Personne exists, retrieve it
                        personne = optionalPersonne.get();
                    } else {
                        // Personne does not exist, create a new one
                        personne = new Personne();
                        personne.setNom(nom);
                        personne.setPrenom(prenom);
                        personne.setDateNaissance(dateNaissance);
                        personne.setLieuNaissance(lieuNaissance);

                        try {
                            personneRepository.save(personne); // Save the new Personne to the database
                        } catch (Exception e) {
                            System.err.println("Error saving Personne: " + nom + " " + prenom + " - " + e.getMessage());
                            e.printStackTrace();
                            continue; // Skip to the next line
                        }
                    }

                    // Check if the Realisateur already exists
                    Optional<Realisateur> optionalRealisateur = realisateurRepository.findById(personne.getId());

                    if (optionalRealisateur.isEmpty()) {
                        // Create a new Realisateur if not already present
                        Realisateur realisateur = new Realisateur();
                        realisateur.setIdImdb(imdbId);
                        realisateur.setCreatedDate(LocalDateTime.now());

                        try {
                            realisateurRepository.save(realisateur); // Save the new Realisateur to the database
                        } catch (Exception e) {
                            System.err.println("Error saving Realisateur: " + nom + " " + prenom + " - " + e.getMessage());
                            e.printStackTrace();
                        }
                    }

                } catch (Exception e) {
                    System.err.println("Error processing line for realisateur: " + (line.length > 1 ? line[0] : "Unknown") + " - " + e.getMessage());
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

    private LocalDate parseDate(String dateStr) {
        if (dateStr == null || "N/A".equals(dateStr)) {
            return null;
        }
        for (DateTimeFormatter formatter : DATE_FORMATTERS) {
            try {
                return LocalDate.parse(dateStr, formatter);
            } catch (DateTimeParseException e) {
                // Continue to try other formats
            }
        }
        System.err.println("Unparseable date: " + dateStr);
        return null;
    }
}
