package Entities.Business.Role;

import Entities.Business.Film.Film;
import Entities.Business.Personne.Acteur; // Import the Acteur class
import jakarta.persistence.*;

@Entity
@Table(name = "Roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column(name = "film" )
    private String idbmfilm;
    @Column(name = "acteur")
    private String idbmacteur;

    @Column(name = "role_name", nullable = false)
    private String roleName;

    @ManyToOne
    @JoinColumn(name = "film_imdb", nullable = false)
    private Film film;

    @ManyToOne
    @JoinColumn(name = "actor_id_imdb", nullable = false)
    private Acteur actor; // Correctly reference Acteur

    // Default constructor
    public Role() {}

    // Parameterized constructor
    public Role(String roleName, Film film, Acteur actor, String idbmacteur, String idbmfilm) {
        this.roleName = roleName;
        this.film = film;
        this.actor = actor;
        this.idbmacteur= idbmacteur;
        this.idbmfilm = idbmfilm;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }
    public String getIdbmacteur() {
        return idbmacteur;
    }

    public void setIdbmacteur(String idbmacteur) {
        this.idbmacteur = idbmacteur;
    }

    public String getIdbmfilm() {
        return idbmfilm;
    }

    public void setIdbmfilm(String idbmfilm) {
        this.idbmfilm = idbmfilm;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Film getImdb() {
        return getImdb();
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Acteur getid_imdb() {
        return getid_imdb();
    }

    public void setActor(Acteur actor) {
        this.actor = actor;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", film=" + getImdb() +
                ", actor=" + getid_imdb() +
                '}';
    }
}
