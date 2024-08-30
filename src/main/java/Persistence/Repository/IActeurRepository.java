package Persistence.Repository;

import Entities.Business.Personne.Acteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IActeurRepository extends JpaRepository<Acteur, Long> {

    // Find by Nom
    List<Acteur> findByNom(String nom);

    // Find by Date of Birth after a specific date
    List<Acteur> findByDateNaissanceAfter(LocalDateTime date);

    // Find by Date of Birth before a specific date
    List<Acteur> findByDateNaissanceBefore(LocalDateTime date);

    // Find by Date of Birth Range
    List<Acteur> findByDateNaissanceBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Find All Acteurs Ordered by Nom (Alphabetical Order)
    List<Acteur> findAllByOrderByNomAsc();

    // Find All Acteurs Ordered by Nom (Reverse Alphabetical Order)
    List<Acteur> findAllByOrderByNomDesc();

    Optional<Acteur> findById(Long id);

    @Query("SELECT a FROM Acteur a WHERE a.idImdb = :imdb")
    Acteur findByImdb(@Param("imdb") String imdb);
}