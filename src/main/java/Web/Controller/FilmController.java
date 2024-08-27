package Web.Controller;

import Entities.Business.Film.Film;
import Exceptions.EntityNotFoundException;
import Exceptions.InvalidDataException;
import Service.FilmService;

import Web.Model.DTO.FilmDTO;
import Web.Model.Generic.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/films")
public class FilmController {

    @Autowired
    private FilmService filmService;

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<FilmDTO>>> searchFilms(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) Integer annee,
            @RequestParam(required = false) Double rating,
            @RequestParam(required = false) String paysName,
            @RequestParam(required = false) String genreName,
            @RequestParam(required = false) String sortBy
    ) {
        try {
            List<FilmDTO> films = filmService.findFilmsWithFiltersAndSorting(nom, annee, rating, paysName, genreName, sortBy);
            ApiResponse<List<FilmDTO>> response = new ApiResponse<>(HttpStatus.OK.value(), "Films retrieved successfully", films);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InvalidDataException ex) {
            throw ex;  // Handled by the Global Exception Handler
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while searching for films", ex);  // Handled by the Global Exception Handler
        }
    }

    @GetMapping("/imdb/{imdb}")
    public ResponseEntity<ApiResponse<List<FilmDTO>>> getFilmsByImdb(@PathVariable String imdb) {
        try {
            List<FilmDTO> films = filmService.findFilmsByImdb(imdb);
            if (films.isEmpty()) {
                throw new EntityNotFoundException("No films found with IMDb ID: " + imdb);
            }
            ApiResponse<List<FilmDTO>> response = new ApiResponse<>(HttpStatus.OK.value(), "Films retrieved successfully", films);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            throw ex;  // Handled by the Global Exception Handler
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while retrieving films by IMDb", ex);  // Handled by the Global Exception Handler
        }
    }

    @GetMapping("/name/{nom}")
    public ResponseEntity<ApiResponse<List<FilmDTO>>> getFilmsByName(@PathVariable String nom) {
        try {
            List<FilmDTO> films = filmService.findFilmsByName(nom);
            if (films.isEmpty()) {
                throw new EntityNotFoundException("No films found with name: " + nom);
            }
            ApiResponse<List<FilmDTO>> response = new ApiResponse<>(HttpStatus.OK.value(), "Films retrieved successfully", films);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            throw ex;  // Handled by the Global Exception Handler
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while retrieving films by name", ex);  // Handled by the Global Exception Handler
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<FilmDTO>> createFilm(@RequestBody FilmDTO filmDTO) {
        try {
            FilmDTO createdFilm = filmService.createFilm(filmDTO);
            ApiResponse<FilmDTO> response = new ApiResponse<>(HttpStatus.CREATED.value(), "Film created successfully", createdFilm);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (InvalidDataException ex) {
            throw ex;  // Handled by the Global Exception Handler
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while creating the film", ex);  // Handled by the Global Exception Handler
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<FilmDTO>> updateFilm(@PathVariable Long id, @RequestBody FilmDTO filmDTO) {
        try {
            FilmDTO updatedFilm = filmService.updateFilm(id, filmDTO);
            ApiResponse<FilmDTO> response = new ApiResponse<>(HttpStatus.OK.value(), "Film updated successfully", updatedFilm);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            throw ex;  // Handled by the Global Exception Handler
        } catch (InvalidDataException ex) {
            throw ex;  // Handled by the Global Exception Handler
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while updating the film", ex);  // Handled by the Global Exception Handler
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteFilm(@PathVariable Long id) {
        try {
            filmService.deleteFilm(id);
            ApiResponse<Void> response = new ApiResponse<>(HttpStatus.NO_CONTENT.value(), "Film deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException ex) {
            throw ex;  // Handled by the Global Exception Handler
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while deleting the film", ex);  // Handled by the Global Exception Handler
        }
    }

    @GetMapping("/by-actor/{actorId}")
    public ResponseEntity<List<Film>> getFilmsByActor(@PathVariable Long actorId) {
        List<Film> films = filmService.findFilmsByActor(actorId);
        return ResponseEntity.ok(films);
    }
}
