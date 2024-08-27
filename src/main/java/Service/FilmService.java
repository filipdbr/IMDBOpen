package Service;

import Entities.Business.Film.Film;
import Exceptions.EntityNotFoundException;
import Exceptions.InvalidDataException;
import Persistence.Repository.IFilmRepository;

import Web.Model.DTO.FilmDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

    @Autowired
    private IFilmRepository filmRepository;

    public List<FilmDTO> findFilmsWithFiltersAndSorting(String nom, Integer annee, Double rating, String paysName, String genreName, String sortBy) {
        List<Film> films = filmRepository.findAll(); // Replace with a more specific query logic if needed

        // Filtering logic
        if (StringUtils.hasText(nom)) {
            films = films.stream().filter(film -> film.getNom().toLowerCase().contains(nom.toLowerCase())).collect(Collectors.toList());
        }
        if (annee != null) {
            films = films.stream().filter(film -> film.getAnnee() == annee).collect(Collectors.toList());
        }
        if (rating != null) {
            films = films.stream().filter(film -> film.getRating() == rating).collect(Collectors.toList());
        }
        if (StringUtils.hasText(paysName)) {
            films = films.stream().filter(film -> film.getPaysList().stream().anyMatch(pays -> pays.getName().equalsIgnoreCase(paysName))).collect(Collectors.toList());
        }
        if (StringUtils.hasText(genreName)) {
            films = films.stream().filter(film -> film.getGenres().stream().anyMatch(genre -> genre.getName().equalsIgnoreCase(genreName))).collect(Collectors.toList());
        }

        // Sorting logic
        if (StringUtils.hasText(sortBy)) {
            // Check if sorting should be ascending or descending based on the parameter
            boolean ascending = !sortBy.startsWith("-");
            String sortField = ascending ? sortBy : sortBy.substring(1);

            if (ascending) {
                films = films.stream()
                        .sorted((f1, f2) -> compareFilmsByField(f1, f2, sortField))
                        .collect(Collectors.toList());
            } else {
                films = films.stream()
                        .sorted((f1, f2) -> compareFilmsByField(f2, f1, sortField))
                        .collect(Collectors.toList());
            }
        }

        if (films.isEmpty()) {
            throw new EntityNotFoundException("No films found matching the criteria");
        }

        return films.stream().map(FilmDTO::fromEntity).collect(Collectors.toList());
    }

    private int compareFilmsByField(Film f1, Film f2, String field) {
        switch (field) {
            case "nom":
                return f1.getNom().compareToIgnoreCase(f2.getNom());
            case "annee":
                return Integer.compare(f1.getAnnee(), f2.getAnnee());
            case "rating":
                return Double.compare(f1.getRating(), f2.getRating());
            // Add more cases as needed for other fields
            default:
                throw new IllegalArgumentException("Unsupported sorting field: " + field);
        }
    }

    public List<FilmDTO> findFilmsByImdb(String imdb) {
        List<Film> films = filmRepository.findByImdb(imdb);
        if (films.isEmpty()) {
            throw new EntityNotFoundException("No films found with IMDb ID: " + imdb);
        }
        return films.stream().map(FilmDTO::fromEntity).collect(Collectors.toList());
    }

    public List<FilmDTO> findFilmsByName(String nom) {
        List<Film> films = filmRepository.findByNomContainingIgnoreCase(nom);
        if (films.isEmpty()) {
            throw new EntityNotFoundException("No films found with name containing: " + nom);
        }
        return films.stream().map(FilmDTO::fromEntity).collect(Collectors.toList());
    }

    // Additional CRUD operations
    public FilmDTO createFilm(FilmDTO filmDTO) {
        if (filmDTO == null) {
            throw new InvalidDataException("Film data cannot be null");
        }
        Film film = filmDTO.toEntity();
        Film savedFilm = filmRepository.save(film);
        return FilmDTO.fromEntity(savedFilm);
    }

    public FilmDTO updateFilm(Long id, FilmDTO filmDTO) {
        if (filmDTO == null) {
            throw new InvalidDataException("Film data cannot be null");
        }
        Film existingFilm = filmRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Film not found with ID: " + id));
        existingFilm.setNom(filmDTO.getNom());
        existingFilm.setAnnee(filmDTO.getAnnee());
        existingFilm.setRating(filmDTO.getRating());
        // Update other fields similarly

        Film updatedFilm = filmRepository.save(existingFilm);
        return FilmDTO.fromEntity(updatedFilm);
    }

    public void deleteFilm(Long id) {
        Film film = filmRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Film not found with ID: " + id));
        filmRepository.delete(film);
    }
}
