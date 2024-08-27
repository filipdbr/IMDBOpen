package Persistence.Repository;

import Entities.Business.Personne.Acteur;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IActeurRepository extends IGenericRepository<Acteur, Long> {


    // Find by Nom and Prenom
    List<Acteur> findByNomAndPrenom(String nom, String prenom);

    // Find by Date of Birth Range
    List<Acteur> findByDateNaissanceBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Find by IMDb ID
    Optional<Acteur> findByIdImdb(Long idImdb);

    // Find All by Role Name
    @Query("SELECT a FROM Acteur a JOIN a.roles r WHERE r.nom = :roleNom")
    List<Acteur> findAllByRoleName(@Param("roleNom") String roleNom);

    // Find by Partial Match of Nom (Case-insensitive)
    List<Acteur> findByNomContainingIgnoreCase(String partialNom);

    // Find by Taille greater than a specified value
    List<Acteur> findByTailleGreaterThan(double taille);

    // Find by Date of Birth after a specific date
    List<Acteur> findByDateNaissanceAfter(LocalDateTime date);

    // Find by CreatedDate within a specific range
    List<Acteur> findByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Find All Acteurs Ordered by Nom (Alphabetical Order)
    List<Acteur> findAllByOrderByNomAsc();

    // Count Acteurs by Role Name
    @Query("SELECT COUNT(a) FROM Acteur a JOIN a.roles r WHERE r.nom = :roleNom")
    long countByRoleName(@Param("roleNom") String roleNom);

    // Find by Nom or Prenom (Case-insensitive search for either field)
    List<Acteur> findByNomIgnoreCaseOrPrenomIgnoreCase(String nom, String prenom);

    // Find Top N Acteurs by Taille (e.g., tallest actors)
    List<Acteur> findTop3ByOrderByTailleDesc();

    // Find Acteurs with No Roles Assigned
    @Query("SELECT a FROM Acteur a WHERE a.roles IS EMPTY")
    List<Acteur> findActeursWithNoRoles();
}
