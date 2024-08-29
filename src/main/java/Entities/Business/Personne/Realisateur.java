package Entities.Business.Personne;

import Entities.Business.Film.Film;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.nio.MappedByteBuffer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "realisateur")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Realisateur extends Personne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_realisateur")
    private Long idRealisateur;

    @Column(name = "id_imdb")
    private long idImdb;

    @OneToMany(mappedBy = "realisateur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Film> films;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public Realisateur(String nom, String prenom, LocalDateTime datenaissance, long idRealisateur, long idImdb, LocalDateTime createdDate, LocalDateTime updatedDate){
        super.setNom(nom);
        super.setPrenom(prenom);
        super.setDateNaissance(datenaissance);
        this.idRealisateur = idRealisateur;
        this.idImdb = idImdb;
        this.films = new ArrayList<>();
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    /**
     * @return
     */
    @Override
    public Long getId() {
        return this.idRealisateur;
    }

    /**
     * @param aLong
     */
    @Override
    public void setId(Long aLong) {
        this.idRealisateur = aLong;

    }

    /**
     * @return
     */
    @Override
    public LocalDateTime getCreatedDate() {
        return this.createdDate;
    }

    /**
     * @param createdDate
     */
    @Override
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return
     */
    @Override
    public LocalDateTime getUpdatedDate() {
        return this.updatedDate;
    }

    /**
     * @param updatedDate
     */
    @Override
    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return
     */
    @Override
    public boolean isDeleted() {
        return false;
    }

    /**
     * @param deleted
     */
    @Override
    public void setDeleted(boolean deleted) {
    }

    /**
     * @return
     */
    @Override
    public String getNom() {
        return super.getNom();
    }

    /**
     * @param nom
     */
    @Override
    public void setNom(String nom) {
        super.setNom(nom);
    }

    /**
     * @return
     */
    @Override
    public String getPrenom() {
        return super.getPrenom();
    }

    /**
     * @param prenom
     */
    @Override
    public void setPrenom(String prenom) {
        super.setPrenom(prenom);
    }

    /**
     * @return
     */
    @Override
    public LocalDateTime getDateNaissance() {
        return super.getDateNaissance();
    }

    /**
     * @param dateNaissance
     */
    @Override
    public void setDateNaissance(LocalDateTime dateNaissance) {
        super.setDateNaissance(dateNaissance);
    }



}