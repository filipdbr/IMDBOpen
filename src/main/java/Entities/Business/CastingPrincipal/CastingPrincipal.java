package Entities.Business.CastingPrincipal;

import Entities.Business.Film.Film;
import Entities.Business.Personne.Acteur;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "casting_principal")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CastingPrincipal {


    // clé primaire
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_casting")
    private Long idCasting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acteur_id", nullable = false)
    private Acteur acteur;

    // additional attributs
    // todo Bernardo please decide if we keep those attributs or not, we created them as extras
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate;

    // getters and setters

    public Long getIdCasting() {
        return idCasting;
    }

    public void setIdCasting(Long idCasting) {
        this.idCasting = idCasting;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Acteur getActeur() {
        return acteur;
    }

    public void setActeur(Acteur acteur) {
        this.acteur = acteur;
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

    @Override
    public String toString() {
        return "CastingPrincipal{" +
                "idCasting=" + idCasting +
                ", film=" + film +
                ", acteur=" + acteur +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }


}