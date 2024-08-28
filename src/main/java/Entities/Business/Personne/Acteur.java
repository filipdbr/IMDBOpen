package Entities.Business.Personne;

import Entities.Business.Film.Film;
import Entities.Business.Role.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Acteur")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Acteur extends Personne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_acteur")
    private Long idActeur;

    @Column(name = "id_imdb")
    private String idImdb;

    @Column(name = "taille")
    private double taille;

    // Many-to-Many relationship with Film
    @ManyToMany
    @JoinTable(
            name = "Acteur_Film",
            joinColumns = @JoinColumn(name = "acteur_id"),
            inverseJoinColumns = @JoinColumn(name = "film_id")
    )
    private List<Film> films = new ArrayList<>();

    // One-to-Many relationship with Role
    @OneToMany(mappedBy = "actor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Role> roles = new ArrayList<>();

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    public Acteur(String idImdb, String nom, String prenom, LocalDateTime dateNaissance, double taille) {
        super.setNom(nom);
        super.setPrenom(prenom);
        super.setDateNaissance(dateNaissance);
        this.idImdb = idImdb;
        this.taille = taille;
        this.createdDate = LocalDateTime.now();
    }

    @Override
    public Long getId() {
        return this.idActeur;
    }

    @Override
    public void setId(Long id) {
        this.idActeur = id;
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
