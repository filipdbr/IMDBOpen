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

    @Column(name = "film_id")
    private String filmId; // ID in the film table, referenced as a String

    @Column(name = "acteur_id")
    private String acteurId; // ID in the acteur table, referenced as a String


    // getters and setters

    public Long getIdCasting() {
        return idCasting;
    }

    public void setIdCasting(Long idCasting) {
        this.idCasting = idCasting;
    }





    @Override
    public String toString() {
        return "CastingPrincipal{" +
                "idCasting=" + idCasting +
                ", film=" + filmId +
                ", acteur=" + acteurId +
                '}';
    }


}