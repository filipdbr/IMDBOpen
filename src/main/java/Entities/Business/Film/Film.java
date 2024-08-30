package Entities.Business.Film;

import Entities.Business.Pays.Pays;
import Entities.Business.Personne.Acteur;
import Entities.Business.Role.Role;
import Entities.Generic.IEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Film")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Film implements IEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "imdb")
    @NotBlank(message = "IMDb identifier cannot be blank")
    @Size(max = 50, message = "IMDb identifier should not exceed 50 characters")
    private String imdb;

    @Column(name = "nom")
    @NotBlank(message = "Film name cannot be blank")
    @Size(max = 255, message = "Film name should not exceed 255 characters")
    private String nom;

    @Column(name = "annee")
    @NotBlank(message = "Année cannot be blank")
    @Size(max = 10, message = "Année should not exceed 10 characters")
    private String annee;

    @Column(name = "rating")
    @NotBlank(message = "Rating cannot be blank")
    @Size(max = 4, message = "Rating should not exceed 4 characters")
    private String rating;

    @Column(name = "url")
    @Size(max = 500, message = "URL should not exceed 500 characters")
    private String url;

    @Column(name = "lieu_tour")
    @Size(max = 2255, message = "Filming location should not exceed 2255 characters")
    private String lieuTour;

    @Column(name = "langue")
    @NotBlank(message = "Language cannot be blank")
    @Size(max = 100, message = "Language should not exceed 100 characters")
    private String langue;

    @Column(name = "resume", columnDefinition = "TEXT")
    @NotBlank(message = "Resume cannot be blank")

    private String resume;

    @Column(name = "id_pays")
    private String pays;

    @ManyToMany(mappedBy = "films")
    private List<Acteur> acteurs = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "Film_Genre",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    @ManyToMany
    @JoinTable(
            name = "Film_Pays",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "pays_id")
    )
    private List<Pays> paysList;

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Role> roles = new ArrayList<>();

    @Override
    @Transient
    public LocalDateTime getCreatedDate() {
        return null; // Implement as needed
    }

    @Override
    @Transient
    public void setCreatedDate(LocalDateTime createdDate) {
        // Implement as needed
    }

    @Override
    @Transient
    public LocalDateTime getUpdatedDate() {
        return null; // Implement as needed
    }

    @Override
    @Transient
    public void setUpdatedDate(LocalDateTime updatedDate) {
        // Implement as needed
    }

    @Override
    @Transient
    public boolean isDeleted() {
        return false; // Implement as needed
    }

    @Override
    @Transient
    public void setDeleted(boolean deleted) {
        // Implement as needed
    }
}
