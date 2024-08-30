package Entities.Business.Personne;

import Entities.Business.Film.Film;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("REALISATEUR")
public class Realisateur extends Personne {

    @Column(name = "id_imdb")
    private String idImdb;

    @ManyToMany
    @JoinTable(
            name = "Film_Realisateur",
            joinColumns = @JoinColumn(name = "realisateur_id"),
            inverseJoinColumns = @JoinColumn(name = "film_id")
    )
    private List<Film> films = new ArrayList<>();

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    public Realisateur(String nom, String prenom, LocalDate dateNaissance, String lieuNaissance, String url, String idImdb) {
        super.setNom(nom);
        super.setPrenom(prenom);
        super.setDateNaissance(dateNaissance);
        super.setLieuNaissance(lieuNaissance);
        super.setUrl(url);
        this.idImdb = idImdb;
        this.createdDate = LocalDateTime.now();
    }

    @Override
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    @Override
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    @Override
    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public boolean isDeleted() {
        return false; // Modify as needed
    }

    @Override
    public void setDeleted(boolean deleted) {
        // Implement as needed
    }
}
