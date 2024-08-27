package Entities.Business.Film;

import Entities.Business.Pays;
import Entities.Generic.IEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
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
    private Long id;

    @NotBlank(message = "IMDb identifier cannot be blank")
    @Size(max = 50, message = "IMDb identifier should not exceed 50 characters")
    private String imdb;

    @NotBlank(message = "Film name cannot be blank")
    @Size(max = 255, message = "Film name should not exceed 255 characters")
    private String nom;

    @Min(value = 1888, message = "Year must be greater than or equal to 1888")
    @Max(value = 2100, message = "Year must be less than or equal to 2100")
    private int annee;

    @DecimalMin(value = "0.0", inclusive = true, message = "Rating must be between 0.0 and 10.0")
    @DecimalMax(value = "10.0", inclusive = true, message = "Rating must be between 0.0 and 10.0")
    private double rating;

    @Size(max = 500, message = "URL should not exceed 500 characters")
    private String url;

    @Size(max = 255, message = "Filming location should not exceed 255 characters")
    private String lieuTour;

    @NotBlank(message = "Language cannot be blank")
    @Size(max = 100, message = "Language should not exceed 100 characters")
    private String langue;

    @Size(max = 2000, message = "Summary should not exceed 2000 characters")
    private String resume;

    @Size(max = 255, message = "Country should not exceed 255 characters")
    private String pays;

    @ManyToMany
    @JoinTable(
            name = "Film_Genre",
            joinColumns = @JoinColumn(name = "FilmID"),
            inverseJoinColumns = @JoinColumn(name = "GenreID")
    )
    private List<Genre> genres;

    @ManyToMany
    @JoinTable(
            name = "Film_Pays",
            joinColumns = @JoinColumn(name = "FilmID"),
            inverseJoinColumns = @JoinColumn(name = "PaysID")
    )
    private List<Pays> paysList;

    @Override
    @Transient
    public LocalDateTime getCreatedDate() {
        return null; // or implement as needed
    }

    @Override
    @Transient
    public void setCreatedDate(LocalDateTime createdDate) {
        // or implement as needed
    }

    @Override
    @Transient
    public LocalDateTime getUpdatedDate() {
        return null; // or implement as needed
    }

    @Override
    @Transient
    public void setUpdatedDate(LocalDateTime updatedDate) {
        // or implement as needed
    }

    @Override
    @Transient
    public boolean isDeleted() {
        return false; // or implement as needed
    }

    @Override
    @Transient
    public void setDeleted(boolean deleted) {
        // or implement as needed
    }
}
