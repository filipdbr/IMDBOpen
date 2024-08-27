package Controller;

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
        List<FilmDTO> films = filmService.findFilmsWithFiltersAndSorting(nom, annee, rating, paysName, genreName, sortBy);
        ApiResponse<List<FilmDTO>> response = new ApiResponse<>(HttpStatus.OK.value(), "Films retrieved successfully", films);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/imdb/{imdb}")
    public ResponseEntity<ApiResponse<List<FilmDTO>>> getFilmsByImdb(@PathVariable String imdb) {
        List<FilmDTO> films = filmService.findFilmsByImdb(imdb);
        ApiResponse<List<FilmDTO>> response = new ApiResponse<>(HttpStatus.OK.value(), "Films retrieved successfully", films);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/name/{nom}")
    public ResponseEntity<ApiResponse<List<FilmDTO>>> getFilmsByName(@PathVariable String nom) {
        List<FilmDTO> films = filmService.findFilmsByName(nom);
        ApiResponse<List<FilmDTO>> response = new ApiResponse<>(HttpStatus.OK.value(), "Films retrieved successfully", films);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<FilmDTO>> createFilm(@RequestBody FilmDTO filmDTO) {
        FilmDTO createdFilm = filmService.createFilm(filmDTO);
        ApiResponse<FilmDTO> response = new ApiResponse<>(HttpStatus.CREATED.value(), "Film created successfully", createdFilm);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<FilmDTO>> updateFilm(@PathVariable Long id, @RequestBody FilmDTO filmDTO) {
        FilmDTO updatedFilm = filmService.updateFilm(id, filmDTO);
        ApiResponse<FilmDTO> response = new ApiResponse<>(HttpStatus.OK.value(), "Film updated successfully", updatedFilm);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteFilm(@PathVariable Long id) {
        filmService.deleteFilm(id);
        ApiResponse<Void> response = new ApiResponse<>(HttpStatus.NO_CONTENT.value(), "Film deleted successfully", null);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
