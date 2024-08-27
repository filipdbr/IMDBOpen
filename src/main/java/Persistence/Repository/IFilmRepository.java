package Persistence.Repository;

import Entities.Business.Film.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IFilmRepository extends JpaRepository<Film, Long> {

    // Find films by IMDb ID
    List<Film> findByImdb(String imdb);

    // Find films by name (partial match, case insensitive)
    List<Film> findByNomContainingIgnoreCase(String nom);

    // Find films by release year
    List<Film> findByAnnee(int annee);

    // Find films with a rating above a certain threshold
    List<Film> findByRatingGreaterThanEqual(double rating);

    // Find films by country name (assuming 'paysList' contains 'Pays' entities)
    @Query("SELECT f FROM Film f JOIN f.paysList p WHERE p.name = :paysName")
    List<Film> findByPaysName(@Param("paysName") String paysName);

    // Find films by genre name (assuming 'genres' contains 'Genre' entities)
    @Query("SELECT f FROM Film f JOIN f.genres g WHERE g.name = :genreName")
    List<Film> findByGenreName(@Param("genreName") String genreName);

    // Find films by language
    List<Film> findByLangueContainingIgnoreCase(String langue);

    // Find films by location of shooting
    List<Film> findByLieuTourContainingIgnoreCase(String lieuTour);

    // Custom query to find films with multiple filters and sorting
    @Query("SELECT f FROM Film f WHERE "
            + "(:nom IS NULL OR LOWER(f.nom) LIKE LOWER(CONCAT('%', :nom, '%'))) AND "
            + "(:annee IS NULL OR f.annee = :annee) AND "
            + "(:rating IS NULL OR f.rating >= :rating) AND "
            + "(:paysName IS NULL OR EXISTS (SELECT 1 FROM f.paysList p WHERE p.name = :paysName)) AND "
            + "(:genreName IS NULL OR EXISTS (SELECT 1 FROM f.genres g WHERE g.name = :genreName)) "
            + "ORDER BY "
            + "CASE WHEN :sortBy = 'nom' THEN f.nom END ASC, "
            + "CASE WHEN :sortBy = 'annee' THEN f.annee END ASC, "
            + "CASE WHEN :sortBy = 'rating' THEN f.rating END DESC")
    List<Film> findFilmsWithFiltersAndSorting(
            @Param("nom") String nom,
            @Param("annee") Integer annee,
            @Param("rating") Double rating,
            @Param("paysName") String paysName,
            @Param("genreName") String genreName,
            @Param("sortBy") String sortBy
    );
}
