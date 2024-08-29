package Entities.Business.Film;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "Genre")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Genre name cannot be blank")
    @Column(name = "nom")
    @Size(max = 100, message = "Genre name should not exceed 100 characters")
    private String name;

    @ManyToMany(mappedBy = "genres")
    private List<Film> films;

    // Default constructor
    public Genre() {}

    // Constructor with id, name, and films
    public Genre(Long id, String name, List<Film> films) {
        this.id = id;
        this.name = name;
        this.films = films;
    }

    // Constructor with name only
    public Genre(String name) {
        this.name = name;
    }

    public Genre(Object o, String genreName) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }
}