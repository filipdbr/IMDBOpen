package Entities.Business.Personne;

import Entities.Business.Film.Film;
import Entities.Business.Role.Role;
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
@DiscriminatorValue("ACTEUR")
public class Acteur extends Personne {

    @Column(name = "id_imdb")
    private String idImdb;



    @Column(name ="taille")
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

    public Acteur(String idImdb, String nom, String prenom, LocalDate dateNaissance) {
        super.setNom(nom);
        super.setPrenom(prenom);
        super.setDateNaissance(dateNaissance);
        this.idImdb = idImdb;
        this.createdDate = LocalDateTime.now();
    }

    public Long getIdActeur() {
        return super.getId();
    }

    public void setIdActeur(Long id) {
        super.setId(id);
    }

    public void setPersonne(Personne personne) {
        this.setNom(personne.getNom());
        this.setPrenom(personne.getPrenom());
        this.setDateNaissance(personne.getDateNaissance());
        this.setLieuNaissance(personne.getLieuNaissance());
        this.setUrl(personne.getUrl());
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

    public double getTaille() {
        return taille;
    }

    public void setTaille(double taille) {
        this.taille = taille;
    }
}
