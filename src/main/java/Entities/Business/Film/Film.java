package Entities.Business.Film;

import Entities.Business.Pays;
import Entities.Generic.IEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "Film")
@Data
@Builder
public class Film implements IEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String imdb;
    private String nom;
    private int annee;
    private double rating;
    private String url;
    private String lieuTouf;
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

    }

    @Override
    public void setId(Long aLong) {

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