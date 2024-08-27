package Persistence.Repository;

import Entities.Business.Film.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IGenreRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> findByName(String genreName);
}
