package Entities.Business.Personne;

import Entities.Business.Film.Film;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "acteur")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Acteur {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "personne_id", referencedColumnName = "id")
    private Personne personne;

    @Getter
    @Setter
    @Column(name = "id_imdb")
    private String idImdb;

    @Getter
    @Setter
    @Column(name = "taille")
    private String taille;

    //@ManyToMany
    //@JoinTable(
    //        name = "film_acteur",
    //        joinColumns = @JoinColumn(name = "acteur_id_imdb"),
    //        inverseJoinColumns = @JoinColumn(name = "film_imdb")
    //)
    //private List<Film> films = new ArrayList<>();

    public Acteur(String identite, String dateNaissance, String taille, String idImdb) {
        this.personne = new Personne();
        this.personne.setIdentite(identite);
        this.personne.setDateNaissance(dateNaissance);
        this.taille = taille;
        this.idImdb = idImdb;//
    }


}