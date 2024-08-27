package Web.Controller;

import Entities.Business.Film.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import Web.Model.DTO.FilmDTO;
import Persistence.Repository.IGenericRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/films")
public class FilmController {

    @Autowired
    private IGenericRepository<Film, Long> filmRepository;

    @GetMapping
    public List<FilmDTO> getAllFilms() {
        return filmRepository.findAll().stream()
                .map(FilmDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmDTO> getFilmById(@PathVariable Long id) {
        return filmRepository.findById(id)
                .map(film -> ResponseEntity.ok(FilmDTO.fromEntity(film)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public FilmDTO createFilm(@RequestBody FilmDTO filmDTO) {
        Film film = filmDTO.toEntity();
        film = filmRepository.save(film);
        return FilmDTO.fromEntity(film);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FilmDTO> updateFilm(@PathVariable Long id, @RequestBody FilmDTO filmDTO) {
        return filmRepository.findById(id)
                .map(film -> {
                    film.setNom(filmDTO.getNom());
                    film.setImdb(filmDTO.getImdb());
                    // Update other fields as necessary
                    filmRepository.save(film);
                    return ResponseEntity.ok(FilmDTO.fromEntity(film));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFilm(@PathVariable Long id) {
        return filmRepository.findById(id)
                .map(film -> {
                    filmRepository.delete(film);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}