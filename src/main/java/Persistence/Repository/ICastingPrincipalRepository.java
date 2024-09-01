package Persistence.Repository;

import Entities.Business.CastingPrincipal.CastingPrincipal;
import Entities.Business.Film.Film;
import Entities.Business.Personne.Acteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ICastingPrincipalRepository extends JpaRepository<CastingPrincipal, Long> {

    // Find by film
    List<CastingPrincipal> findByFilm(Film film);

    // Find by actor
    List<CastingPrincipal> findByActeur(Acteur acteur);

    // Find by film and actor
    Optional<CastingPrincipal> findByFilmAndActeur(Film film, Acteur acteur);

    // Find by creation date
    List<CastingPrincipal> findByCreatedDate(LocalDateTime createdDate);

    // Find by update date
    List<CastingPrincipal> findByUpdatedDate(LocalDateTime updatedDate);

    // Find by creation date after a specific date
    List<CastingPrincipal> findByCreatedDateAfter(LocalDateTime date);

    // Find by update date before a specific date
    List<CastingPrincipal> findByUpdatedDateBefore(LocalDateTime date);

    // Find by film and creation date range
    List<CastingPrincipal> findByFilmAndCreatedDateBetween(Film film, LocalDateTime startDate, LocalDateTime endDate);

    // Find by actor and update date range
    List<CastingPrincipal> findByActeurAndUpdatedDateBetween(Acteur acteur, LocalDateTime startDate, LocalDateTime endDate);

    // Find by film and actor and creation date
    List<CastingPrincipal> findByFilmAndActeurAndCreatedDate(Film film, Acteur acteur, LocalDateTime createdDate);

    // Find by film ID
    List<CastingPrincipal> findByFilm_Id(Long filmId);

    // Find by actor ID
    List<CastingPrincipal> findByActeur_Id(Long acteurId);

    // Find by list of film IDs
    List<CastingPrincipal> findByFilm_IdIn(List<Long> filmIds);

    // Find by list of actor IDs
    List<CastingPrincipal> findByActeur_IdIn(List<Long> acteurIds);

    // Find by film and sort by creation date descending
    List<CastingPrincipal> findByFilmOrderByCreatedDateDesc(Film film);

    // Find by actor and sort by update date ascending
    List<CastingPrincipal> findByActeurOrderByUpdatedDateAsc(Acteur acteur);

    // Check if exists by film and actor
    boolean existsByFilmAndActeur(Film film, Acteur acteur);

    // Delete by film and actor
    void deleteByFilmAndActeur(Film film, Acteur acteur);

    boolean existsByFilmIdAndActeurId(String filmId, String acteurId);
}
