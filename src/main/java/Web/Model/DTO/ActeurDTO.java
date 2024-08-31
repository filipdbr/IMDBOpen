package Web.Model.DTO;

import Entities.Business.Personne.Acteur;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ActeurDTO {
    private Long id;
    private String idImdb;
    private String taille; // Add taille field
    private String identite;
    private String dateNaissance; // Keep dateNaissance as String
    private String lieuNaissance; // Add lieuNaissance field
    private String url; // Add url field
    private List<FilmDTO> films; // Use FilmDTO instead of Film
    private List<RoleDTO> roles; // Use RoleDTO for roles

    public static ActeurDTO fromEntity(Acteur acteur) {
        ActeurDTO dto = new ActeurDTO();
        dto.setId(acteur.getId());
        dto.setIdImdb(acteur.getIdImdb());
        dto.setTaille(acteur.getTaille()); // Map taille from entity


  // Map roles if necessary

        return dto;
    }

    public Acteur toEntity() {
        Acteur acteur = new Acteur();
        acteur.setIdImdb(this.idImdb);
        acteur.setTaille(this.taille); // Set taille to entity
        return acteur;
    }
}
