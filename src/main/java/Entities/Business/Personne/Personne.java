package Entities.Business.Personne;

import java.time.LocalDateTime;

public abstract class Personne implements IPersonne<Long> {

    private String nom;
    private String prenom;
    private LocalDateTime dateNaissance;

    // Getters and Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDateTime getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDateTime dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

}
