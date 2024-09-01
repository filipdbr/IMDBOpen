package Persistence.Repository;

import Entities.Business.CastingPrincipal.CastingPrincipal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ICastingPrincipalRepository extends JpaRepository<CastingPrincipal, Long> {

    // Find by film
    List<CastingPrincipal> findByFilm(String film);

    // Find by actor
    List<CastingPrincipal> findByActeur(String acteur);

    // Find by film and actor
    Optional<CastingPrincipal> findByFilmAndActeur(String film, String acteur);

    // Find by creation date
    List<CastingPrincipal> findByCreatedDate(LocalDateTime createdDate);

    // Find by update date
    List<CastingPrincipal> findByUpdatedDate(LocalDateTime updatedDate);

    // Find by film ID
    List<CastingPrincipal> findByFilm_Id(Long filmId);

    // Check if exists by film and actor
    boolean existsByFilmAndActeur(String film, String acteur);

    // Delete by film and actor
    void deleteByFilmAndActeur(String film, String acteur);

    boolean existsByFilmIdAndActeurId(String filmId, String acteurId);
}
