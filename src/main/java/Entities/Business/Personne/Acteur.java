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
//@Table(name = "Acteur")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Acteur extends Personne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private Long personnerId;

    @Column(name = "id_imdb")
    private String idImdb;

    @Column(name = "taille")
    private double taille;

    @ManyToMany
    @JoinTable(
            name = "Film_Acteur",
            joinColumns = @JoinColumn(name = "acteur_id"),
            inverseJoinColumns = @JoinColumn(name = "film_id")
    )
    private List<Film> films = new ArrayList<>();

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    public Acteur(String nom, String prenom, LocalDate dateNaissance, double taille, String idImdb) {
        super.setNom(nom);
        super.setPrenom(prenom);
        super.setDateNaissance(dateNaissance);
        this.taille = taille;
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
