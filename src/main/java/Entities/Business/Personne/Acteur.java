package Entities.Business.Personne;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import javax.management.relation.Role;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Acteur")
@Data
@Builder
public class Acteur implements IPersonne<Long> {

    // déclaration des attributs

    // clé primaire, l'id d'acteur
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_acteur")
    private Long idActeur;

    // id de personne
    @Column(name = "id_imdb")
    private long idImdb;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "date_naissance")
    private LocalDateTime dateNaissance;

    @Column(name = "taille")
    private double taille;

    @OneToMany(mappedBy = "acteur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Role> roles;

    // date de création
    // todo question: is it necessary to create a column or I can skip the mapping?
    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    // constructeur vide
    public Acteur() {

    }

    // constructeur avec des parametres
    public Acteur(long idImdb, String nom, String prenom, LocalDateTime dateNaissance, double taille) {
        this.idImdb = idImdb;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.taille = taille;
        this.createdDate = LocalDateTime.now();
        this.roles = new ArrayList<>();
    }

    // méthodes

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDateTime getDateNaissance() {
        return this.dateNaissance;
    }

    public void setDateNaissance(LocalDateTime dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public Long getId() {
        return this.idActeur;
    }

    public void setId(Long aLong) {
        this.idActeur = idActeur;
    }

    public long getIdImdb() {
        return idImdb;
    }

    public void setIdImdb(long idImdb) {
        this.idImdb = idImdb;
    }

    public double getTaille() {
        return taille;
    }

    public void setTaille(double taille) {
        this.taille = taille;
    }

    public LocalDateTime getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public boolean isDeleted() {
        return false;
    }

    public void setDeleted(boolean deleted) {
        // todo how to do it?
    }
}
