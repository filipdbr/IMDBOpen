package Persistence.Repository;

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

    /**

     * Find realisateurs by their last name.

     *

     * @param nom The last name of the realisateur.

     * @return A list of realisateurs with the given last name.

     */

    List<Realisateur> findByNom(String nom);

    /**

     * Find realisateurs by their first name.

     *

     * @param prenom The first name of the realisateur.

     * @return A list of realisateurs with the given first name.

     */

    List<Realisateur> findByPrenom(String prenom);

    /**

     * Find realisateurs by their last name and first name.

     *

     * @param nom The last name of the realisateur.

     * @param prenom The first name of the realisateur.

     * @return A list of realisateurs with the given last name and first name.

     */

    List<Realisateur> findByNomAndPrenom(String nom, String prenom);

    /**

     * Find realisateurs by partial match of their last name.

     *

     * @param partialNom The partial last name of the realisateur.

     * @return A list of realisateurs with last names containing the given partial name.

     */

    List<Realisateur> findByNomContaining(String partialNom);

    /**

     * Find realisateurs by partial match of their first name.

     *

     * @param partialPrenom The partial first name of the realisateur.

     * @return A list of realisateurs with first names containing the given partial name.

     */

    List<Realisateur> findByPrenomContaining(String partialPrenom);



}

