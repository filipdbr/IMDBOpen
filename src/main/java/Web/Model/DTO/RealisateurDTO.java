package Web.Model.DTO;

import Entities.Business.Personne.Realisateur;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RealisateurDTO {

    private Long id; // ID of the Realisateur
    private String nom; // Name of the Realisateur
    private String prenom; // First name of the Realisateur
    private LocalDate dateNaissance; // Birth date of the Realisateur
    private String lieuNaissance; // Birth place of the Realisateur
    private String url; // URL of the Realisateur's profile
    private String idImdb; // IMDb ID of the Realisateur
    private LocalDateTime createdDate; // Date when the Realisateur was created
    private LocalDateTime updatedDate; // Date when the Realisateur was last updated

    // Default constructor
    public RealisateurDTO() {}

    // Parameterized constructor
    public RealisateurDTO(Long id, String nom, String prenom, LocalDate dateNaissance,
                          String lieuNaissance, String url, String idImdb,
                          LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.lieuNaissance = lieuNaissance;
        this.url = url;
        this.idImdb = idImdb;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getLieuNaissance() {
        return lieuNaissance;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIdImdb() {
        return idImdb;
    }

    public void setIdImdb(String idImdb) {
        this.idImdb = idImdb;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    // Static method to convert a Realisateur entity to a RealisateurDTO
    public static RealisateurDTO fromEntity(Realisateur realisateur) {
        return new RealisateurDTO(
                realisateur.getId(),
                realisateur.getNom(),
                realisateur.getPrenom(),
                realisateur.getDateNaissance(),
                realisateur.getLieuNaissance(),
                realisateur.getUrl(),
                realisateur.getIdImdb(),
                realisateur.getCreatedDate(),
                realisateur.getUpdatedDate()
        );
    }

    // Convert RealisateurDTO to Realisateur entity
    public Realisateur toEntity() {
        Realisateur realisateur = new Realisateur();
        realisateur.setId(this.id);
        realisateur.setNom(this.nom);
        realisateur.setPrenom(this.prenom);
        realisateur.setDateNaissance(this.dateNaissance);
        realisateur.setLieuNaissance(this.lieuNaissance);
        realisateur.setUrl(this.url);
        realisateur.setIdImdb(this.idImdb);
        realisateur.setCreatedDate(this.createdDate);
        realisateur.setUpdatedDate(this.updatedDate);
        return realisateur;
    }
}
