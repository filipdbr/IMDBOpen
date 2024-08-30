package Entities.Business.Role;

import Entities.Business.Film.Film;
import Entities.Business.Personne.Acteur;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Setter
    @Getter
    @Column(name = "role_name")
    private String roleName;

    @Setter
    @Getter
    @Column(name = "film_imdb")
    private String filmImdb;

    @Setter
    @Getter
    @Column(name = "actor_imdb")
    private String actorImdb;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "actor_id")
    private Acteur actor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}