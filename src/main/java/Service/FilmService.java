package Service;

import Entities.Business.Film.Film;
import Entities.Business.Film.Genre;
import Entities.Business.Pays.Pays;
import Exceptions.EntityNotFoundException;
import Exceptions.InvalidDataException;
import Persistence.Repository.IFilmRepository;
import Persistence.Repository.IGenreRepository;
import Persistence.Repository.IPaysRepository;
import Web.Model.DTO.FilmDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

    @Autowired
    private IFilmRepository filmRepository;
    @Autowired
    private IGenreRepository genreRepository;
    @Autowired
    private IPaysRepository paysRepository;

    public List<FilmDTO> findFilmsWithFiltersAndSorting(String nom, String annee, String rating, String paysName, String genreName, String sortBy) {
        List<Film> films = filmRepository.findAll();

        if (StringUtils.hasText(nom)) {
            films = films.stream().filter(film -> film.getNom().toLowerCase().contains(nom.toLowerCase())).collect(Collectors.toList());
        }
        if (annee != null) {
            films = films.stream().filter(film -> film.getAnnee().contains(annee)).collect(Collectors.toList());
        }
        if (rating != null) {
            films = films.stream().filter(film -> film.getRating().contains(rating)).collect(Collectors.toList());
        }
        if (StringUtils.hasText(paysName)) {
            films = films.stream().filter(film -> film.getPaysList().stream().anyMatch(pays -> pays.getName().equalsIgnoreCase(paysName))).collect(Collectors.toList());
        }
        if (StringUtils.hasText(genreName)) {
            films = films.stream().filter(film -> film.getGenres().stream().anyMatch(genre -> genre.getName().equalsIgnoreCase(genreName))).collect(Collectors.toList());
        }

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

    private int compareFilmsByField(Film f1, Film f2, String field, boolean ascending) {
        int comparison = 0;
        switch (field) {
            case "nom":
                comparison = f1.getNom().compareToIgnoreCase(f2.getNom());
                break;
            case "annee":
                comparison = CharSequence.compare(f1.getAnnee(), f2.getAnnee());
                break;
            case "rating":
                comparison = CharSequence.compare(f1.getRating(), f2.getRating());
                break;
            default:
                throw new IllegalArgumentException("Unsupported sorting field: " + field);
        }
        return ascending ? comparison : -comparison;
    }

    /**
     * Finds a film by its IMDb ID and converts it to FilmDTO.
     *
     * @param imdb the IMDb ID of the film
     * @return the FilmDTO object if found
     * @throws EntityNotFoundException if no film is found with the given IMDb ID
     */
    public FilmDTO findFilmByImdb(String imdb) {
        Film film = filmRepository.findByImdb(imdb);

        // If film is not found, throw an exception
        if (film == null) {
            throw new EntityNotFoundException("No film found with IMDb ID: " + imdb);
        }

        // Convert Film entity to FilmDTO
        return FilmDTO.fromEntity(film);
    }

    public List<FilmDTO> findFilmsByName(String nom) {
        List<Film> films = filmRepository.findByNomContainingIgnoreCase(nom);
        if (films.isEmpty()) {
            throw new EntityNotFoundException("No films found with name containing: " + nom);
        }
        return films.stream().map(FilmDTO::fromEntity).collect(Collectors.toList());
    }

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

    public List<Film> findFilmsByActor(Long actorId) {
        return filmRepository.findFilmsByActor(actorId);
    }

    public Genre findOrCreateGenre(String genreName) {
        return genreRepository.findByName(genreName)
                .orElseGet(new java.util.function.Supplier<Genre>() {
                    @Override
                    public Genre get() {
                        return genreRepository.save(new Genre(null, genreName));
                    }
                });
    }


    @Transactional
    public Pays findOrCreatePays(String paysName) {
        return paysRepository.findByName(paysName)
                .orElseGet(new java.util.function.Supplier<Pays>() {
                    @Override
                    public Pays get() {
                        return paysRepository.save(new Pays(null, paysName));
                    }
                });
    }
    public void saveAll(List<Film> films){
        filmRepository.saveAll(films);
    }

    public void save(Film film) {
        filmRepository.saveAndFlush(film);
    }




}