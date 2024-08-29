package Service;

import Entities.Business.Personne.Realisateur;
import Persistence.Repository.IRealisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing Realisateur entities.
 *
 * This class provides methods to interact with RealisateurRepository and
 * includes business logic related to Realisateur.
 */
@Service
public class RealisateurService {

    private final IRealisateurRepository realisateurRepository;

    @Autowired
    public RealisateurService(IRealisateurRepository realisateurRepository) {
        this.realisateurRepository = realisateurRepository;
    }

    /**
     * Find all realisateurs.
     *
     * @return A list of all realisateurs.
     */
    public List<Realisateur> findAll() {
        return realisateurRepository.findAll();
    }

    /**
     * Find a realisateur by ID.
     *
     * @param id The ID of the realisateur.
     * @return An Optional containing the realisateur if found, or empty if not.
     */
    public Optional<Realisateur> findById(Long id) {
        return realisateurRepository.findById(id);
    }

    /**
     * Find realisateurs by IMDB ID.
     *
     * @param idImdb The IMDB ID of the realisateur.
     * @return A list of realisateurs with the given IMDB ID.
     */
    public List<Realisateur> findByIdImdb(long idImdb) {
        return realisateurRepository.findByIdImdb(idImdb);
    }

    /**
     * Save a realisateur.
     *
     * @param realisateur The realisateur entity to save.
     * @return The saved realisateur entity.
     */
    public Realisateur save(Realisateur realisateur) {
        return realisateurRepository.save(realisateur);
    }

    /**
     * Update an existing realisateur.
     *
     * @param realisateur The realisateur entity with updated information.
     * @return The updated realisateur entity.
     */
    public Realisateur update(Realisateur realisateur) {
        if (realisateurRepository.existsById(realisateur.getIdRealisateur())) {
            return realisateurRepository.save(realisateur);
        } else {
            throw new IllegalArgumentException("Realisateur not found with ID: " + realisateur.getIdRealisateur());
        }
    }

    /**
     * Delete a realisateur by ID.
     *
     * @param id The ID of the realisateur to delete.
     */
    public void deleteById(Long id) {
        realisateurRepository.deleteById(id);
    }

    /**
     * Find realisateurs by last name.
     *
     * @param nom The last name of the realisateur.
     * @return A list of realisateurs with the given last name.
     */
    public List<Realisateur> findByNom(String nom) {
        return realisateurRepository.findByNom(nom);
    }

    /**
     * Find realisateurs by first name.
     *
     * @param prenom The first name of the realisateur.
     * @return A list of realisateurs with the given first name.
     */
    public List<Realisateur> findByPrenom(String prenom) {
        return realisateurRepository.findByPrenom(prenom);
    }

    /**
     * Find realisateurs by last name and first name.
     *
     * @param nom The last name of the realisateur.
     * @param prenom The first name of the realisateur.
     * @return A list of realisateurs with the given last name and first name.
     */
    public List<Realisateur> findByNomAndPrenom(String nom, String prenom) {
        return realisateurRepository.findByNomAndPrenom(nom, prenom);
    }

    /**
     * Find realisateurs by partial match of last name.
     *
     * @param partialNom The partial last name of the realisateur.
     * @return A list of realisateurs with last names containing the given partial name.
     */
    public List<Realisateur> findByNomContaining(String partialNom) {
        return realisateurRepository.findByNomContaining(partialNom);
    }

    /**
     * Find realisateurs by partial match of first name.
     *
     * @param partialPrenom The partial first name of the realisateur.
     * @return A list of realisateurs with first names containing the given partial name.
     */
    public List<Realisateur> findByPrenomContaining(String partialPrenom) {
        return realisateurRepository.findByPrenomContaining(partialPrenom);
    }
}