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

    /**
     * Finds films based on multiple filters and sorting criteria.
     *
     * @param nom         the name of the film
     * @param annee       the release year of the film
     * @param rating      the minimum rating of the film
     * @param paysName    the country name associated with the film
     * @param genreName   the genre name associated with the film
     * @param sortBy      the field to sort by
     * @return a list of FilmDTO objects
     */
    public List<FilmDTO> findFilmsWithFiltersAndSorting(String nom, Integer annee, Double rating, String paysName, String genreName, String sortBy) {
        // Fetch all films (consider optimizing this for performance with more specific queries)
        List<Film> films = filmRepository.findAll();

        // Apply filters
        if (StringUtils.hasText(nom)) {
            films = films.stream().filter(film -> film.getNom().toLowerCase().contains(nom.toLowerCase())).collect(Collectors.toList());
        }
        if (annee != null) {
            films = films.stream().filter(film -> film.getAnnee() == annee).collect(Collectors.toList());
        }
        if (rating != null) {
            films = films.stream().filter(film -> film.getRating() >= rating).collect(Collectors.toList());
        }
        if (StringUtils.hasText(paysName)) {
            films = films.stream().filter(film -> film.getPaysList().stream().anyMatch(pays -> pays.getName().equalsIgnoreCase(paysName))).collect(Collectors.toList());
        }
        if (StringUtils.hasText(genreName)) {
            films = films.stream().filter(film -> film.getGenres().stream().anyMatch(genre -> genre.getName().equalsIgnoreCase(genreName))).collect(Collectors.toList());
        }

        // Apply sorting
        if (StringUtils.hasText(sortBy)) {
            boolean ascending = !sortBy.startsWith("-");
            String sortField = ascending ? sortBy : sortBy.substring(1);

            films = films.stream()
                    .sorted((f1, f2) -> compareFilmsByField(f1, f2, sortField, ascending))
                    .collect(Collectors.toList());
        }

        if (films.isEmpty()) {
            throw new EntityNotFoundException("No films found matching the criteria");
        }

        return films.stream().map(FilmDTO::fromEntity).collect(Collectors.toList());
    }

    /**
     * Compares two films based on a specific field for sorting.
     *
     * @param f1        the first film
     * @param f2        the second film
     * @param field     the field to sort by
     * @param ascending whether the sort is ascending or descending
     * @return an integer representing the comparison result
     */
    private int compareFilmsByField(Film f1, Film f2, String field, boolean ascending) {
        int comparison = 0;
        switch (field) {
            case "nom":
                comparison = f1.getNom().compareToIgnoreCase(f2.getNom());
                break;
            case "annee":
                comparison = Integer.compare(f1.getAnnee(), f2.getAnnee());
                break;
            case "rating":
                comparison = Double.compare(f1.getRating(), f2.getRating());
                break;
            default:
                throw new IllegalArgumentException("Unsupported sorting field: " + field);
        }
        return ascending ? comparison : -comparison;
    }

    /**
     * Finds films by IMDb ID.
     *
     * @param imdb the IMDb ID of the film
     * @return a list of FilmDTO objects
     */
    public List<FilmDTO> findFilmsByImdb(String imdb) {
        List<Film> films = filmRepository.findByImdb(imdb);
        if (films.isEmpty()) {
            throw new EntityNotFoundException("No films found with IMDb ID: " + imdb);
        }
        return films.stream().map(FilmDTO::fromEntity).collect(Collectors.toList());
    }

    /**
     * Finds films by name.
     *
     * @param nom the name of the film
     * @return a list of FilmDTO objects
     */
    public List<FilmDTO> findFilmsByName(String nom) {
        List<Film> films = filmRepository.findByNomContainingIgnoreCase(nom);
        if (films.isEmpty()) {
            throw new EntityNotFoundException("No films found with name containing: " + nom);
        }
        return films.stream().map(FilmDTO::fromEntity).collect(Collectors.toList());
    }

    /**
     * Creates a new film.
     *
     * @param filmDTO the FilmDTO object to create
     * @return the created FilmDTO object
     */
    public FilmDTO createFilm(FilmDTO filmDTO) {
        if (filmDTO == null) {
            throw new InvalidDataException("Film data cannot be null");
        }
        Film film = filmDTO.toEntity();
        Film savedFilm = filmRepository.save(film);
        return FilmDTO.fromEntity(savedFilm);
    }

    /**
     * Updates an existing film.
     *
     * @param id       the ID of the film to update
     * @param filmDTO  the FilmDTO object with updated data
     * @return the updated FilmDTO object
     */
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

    /**
     * Deletes a film by ID.
     *
     * @param id the ID of the film to delete
     */
    public void deleteFilm(Long id) {
        Film film = filmRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Film not found with ID: " + id));
        filmRepository.delete(film);
    }
}
