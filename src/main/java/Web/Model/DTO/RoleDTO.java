package Web.Model.DTO;

import Entities.Business.Film.Film;
import Entities.Business.Personne.Acteur;
import Entities.Business.Personne.Personne;
import Entities.Business.Role.Role;

public class RoleDTO {

    private Long id;
    private String roleName;
    private Film film;
    private Acteur actor;

    // Default constructor
    public RoleDTO() {}

    // Parameterized constructor
    public RoleDTO(Long id, String roleName, Film film, Acteur actor) {
        this.id = id;
        this.roleName = roleName;
        this.film = film;
        this.actor = actor;
    }

    // Getters and Setters
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

    // Static method to convert a Role entity to a RoleDTO
    public static RoleDTO fromEntity(Role role) {
        return new RoleDTO(
                role.getId(),
                role.getRoleName(),
                role.getImdb(),
                role.getid_imdb()
        );
    }

    // Convert RoleDTO to Role entity
    public Role toEntity() {
        Role role = new Role();
        role.setId(this.id);
        role.setRoleName(this.roleName);
        role.setFilm(this.film);
        role.setActor(this.actor);
        return role;
    }
}
