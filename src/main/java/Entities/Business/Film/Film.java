package Entities.Business.Film;

import Entities.Business.Pays;
import Entities.Generic.IEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Film")
@Data
@Builder
public class Film implements IEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imdb;
    private String nom;
    private int annee;
    private double rating;
    private String url;
    @Setter
    @Getter
    private String lieuTour;
    private String langue;
    private String resume;
    private String pays;

    @ManyToMany
    @JoinTable(name = "Film_Genre",
            joinColumns = @JoinColumn(name = "FilmID"),
            inverseJoinColumns = @JoinColumn(name = "Genre"))
    private List<Genre> genres;

    @ManyToMany
    @JoinTable(name = "Film_Pays",
            joinColumns = @JoinColumn(name = "FilmID"),
            inverseJoinColumns = @JoinColumn(name = "Pays"))
    private List<Pays> paysList;

    public Film() {
        // No-argument constructor
    }

    public Film(Long id, String imdb, String nom, int annee, double rating, String url, String lieuTour, String langue, String resume, String pays, List<Genre> genres, List<Pays> paysList) {
        this.id = id;
        this.imdb = imdb;
        this.nom = nom;
        this.annee = annee;
        this.rating = rating;
        this.url = url;
        this.lieuTour = lieuTour;
        this.langue = langue;
        this.resume = resume;
        this.pays = pays;
        this.genres = genres;
        this.paysList = paysList;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public LocalDateTime getCreatedDate() {
        return null;
    }

    @Override
    public void setCreatedDate(LocalDateTime createdDate) {

    }

    @Override
    public LocalDateTime getUpdatedDate() {
        return null;
    }

    @Override
    public void setUpdatedDate(LocalDateTime updatedDate) {

    }

    @Override
    public boolean isDeleted() {
        return false;
    }

    @Override
    public void setDeleted(boolean deleted) {

    }
}