package Entities.Business.Role;

import Entities.Business.Film.Film;
import Entities.Business.Personne.Acteur; // Import the Acteur class
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name", nullable = false)
    private String roleName;

    @ManyToOne
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;

    @ManyToOne
    @JoinColumn(name = "actor_id", nullable = false)
    private Acteur actor; // Correctly reference Acteur

    // Default constructor
    public Role() {}

    // Parameterized constructor
    public Role(String roleName, Film film, Acteur actor) {
        this.roleName = roleName;
        this.film = film;
        this.actor = actor;
    }

    // Getters and setters
    public Long getId() {
        return id;
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

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Acteur getActor() {
        return actor;
    }

    public void setActor(Acteur actor) {
        this.actor = actor;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", film=" + film +
                ", actor=" + actor +
                '}';
    }
}
