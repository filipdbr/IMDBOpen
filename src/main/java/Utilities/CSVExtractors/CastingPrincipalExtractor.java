package Utilities.CSVExtractors;

import Entities.Business.CastingPrincipal.CastingPrincipal;
import Entities.Business.Film.Film;
import Entities.Business.Personne.Acteur;
import Persistence.Repository.ICastingPrincipalRepository;
import Persistence.Repository.IFilmRepository;
import Persistence.Repository.IActeurRepository;
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
public class CastingPrincipalExtractor {

    @Autowired
    private ICastingPrincipalRepository castingPrincipalRepository;

    @Autowired
    private IFilmRepository filmRepository;

    @Autowired
    private IActeurRepository acteurRepository;

    @Transactional
    public void extractCastingPrincipalsFromCSV(String filePath) {
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(filePath))
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                .build()) {

            // Read the header row
            reader.readNext();

            String[] line;
            while ((line = reader.readNext()) != null) {
                String filmId = line[0].isEmpty() ? null : line[0].trim();
                String acteurId = line[1].isEmpty() ? null : line[1].trim();

                try {
                    // Check if the CastingPrincipal already exists in the database
                    if (castingPrincipalRepository.existsByFilmIdAndActeurId(filmId, acteurId)) {
                        System.out.println("CastingPrincipal already exists - Film ID: " + filmId + ", Actor ID: " + acteurId);
                    } else {
                        // Create a new CastingPrincipal entity
                        CastingPrincipal castingPrincipal = new CastingPrincipal();
                        castingPrincipal.setFilmId(filmId);
                        castingPrincipal.setActeurId(acteurId);

                        // Set Film and Acteur based on IDs if they exist
                        Optional<Film> optionalFilm = filmRepository.findByImdb(filmId);
                        Optional<Acteur> optionalActeur = acteurRepository.findByImdb(acteurId);

                        if (optionalFilm.isPresent() && optionalActeur.isPresent()) {
                            try {
                                // Save the new CastingPrincipal to the database
                                castingPrincipalRepository.save(castingPrincipal);
                            } catch (Exception e) {
                                System.err.println("Error saving CastingPrincipal - Film ID: " + filmId + ", Actor ID: " + acteurId);
                                e.printStackTrace();
                            }
                        } else {
                            System.err.println("Film or Actor not found for CastingPrincipal - Film ID: " + filmId + ", Actor ID: " + acteurId);
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Error processing line for CastingPrincipal - Film ID: " + filmId + ", Actor ID: " + acteurId + ": " + e.getMessage());
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
