package Persistence.Repository;

import Entities.Business.Personne.Personne;
import Entities.Business.Personne.Realisateur;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**

 * Repository interface for accessing Realisateur entities.

 *

 * This interface extends JpaRepository to provide CRUD operations and query methods

 * for the Realisateur entity. Spring Data JPA will generate the implementation automatically

 * based on the method names.

 */

@Repository

public interface IRealisateurRepository extends JpaRepository<Realisateur, Long> {

    /**

     * Find realisateurs by their IMDB ID.

     *

     * @param idImdb The IMDB ID of the realisateur.

     * @return A list of realisateurs associated with the given IMDB ID.

     */

    List<Realisateur> findByIdImdb(String idImdb);
    Optional<Realisateur> findByPersonne(Personne personne);










}
