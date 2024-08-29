package Web.Model.DTO;

import Entities.Business.Personne.Acteur;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ActeurDTO {
    private Long id;
    private String idImdb;
    private String nom;
    private String prenom;
    private LocalDateTime dateNaissance;
    private List<FilmDTO> films; // Use FilmDTO instead of Film
    private List<RoleDTO> roles; // Use RoleDTO for roles

    public static ActeurDTO fromEntity(Acteur acteur) {
        ActeurDTO dto = new ActeurDTO();
        dto.setId(acteur.getIdActeur());
        dto.setIdImdb(acteur.getIdImdb());
        dto.setNom(acteur.getNom());
        dto.setPrenom(acteur.getPrenom());
        dto.setDateNaissance(acteur.getDateNaissance());
        dto.setFilms(acteur.getFilms().stream().map(FilmDTO::fromEntity).collect(Collectors.toList()));
        dto.setRoles(acteur.getRoles().stream().map(RoleDTO::fromEntity).collect(Collectors.toList()));
        return dto;
    }

    public Acteur toEntity() {
        Acteur acteur = new Acteur();
        acteur.setIdActeur(this.id);
        acteur.setIdImdb(this.idImdb);
        acteur.setNom(this.nom);
        acteur.setPrenom(this.prenom);
        acteur.setDateNaissance(this.dateNaissance);
        acteur.setFilms(this.getFilms().stream().map(FilmDTO::toEntity).collect(Collectors.toList()));
        acteur.setRoles(this.getRoles().stream().map(RoleDTO::toEntity).collect(Collectors.toList()));
        return acteur;
    }
}