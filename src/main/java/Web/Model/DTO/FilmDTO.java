package Web.Model.DTO;

import Entities.Business.Film.Film;
import lombok.Data;

@Data
public class FilmDTO {
    private Long id;
    private String imdb;
    private String nom;
    private int annee;
    private double rating;
    private String url;
    private String lieuTouf;
    private String langue;
    private String resume;
    private String pays;

    public static FilmDTO fromEntity(Film film) {
        FilmDTO dto = new FilmDTO();
        dto.setId(film.getId());
        dto.setImdb(film.getImdb());
        dto.setNom(film.getNom());
        dto.setAnnee(film.getAnnee());
        dto.setRating(film.getRating());
        dto.setUrl(film.getUrl());
        dto.setLieuTouf(film.getLieuTouf());
        dto.setLangue(film.getLangue());
        dto.setResume(film.getResume());
        dto.setPays(film.getPays());
        return dto;
    }

    public Film toEntity() {
        Film film = new Film();
        film.setId(this.id);
        film.setImdb(this.imdb);
        film.setNom(this.nom);
        film.setAnnee(this.annee);
        film.setRating(this.rating);
        film.setUrl(this.url);
        film.setLieuTouf(this.lieuTouf);
        film.setLangue(this.langue);
        film.setResume(this.resume);
        film.setPays(this.pays);
        return film;
    }
}