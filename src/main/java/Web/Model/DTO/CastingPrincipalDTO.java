package Web.Model.DTO;

import Entities.Business.CastingPrincipal.CastingPrincipal;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for the CastingPrincipal entity.
 * <p>
 * This class is used to transfer data between different layers of the application
 * without exposing the full entity.
 * </p>
 *
 * @author mbenyahia, fbellin, fdabrowski
 */
@Data
public class CastingPrincipalDTO {

    private Long id;
    private FilmDTO film;
    private ActeurDTO acteur;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    /**
     * Converts a CastingPrincipal entity to a CastingPrincipalDTO.
     *
     * @param castingPrincipal the CastingPrincipal entity to be converted
     * @return the corresponding CastingPrincipalDTO
     */
    public static CastingPrincipalDTO fromEntity(CastingPrincipal castingPrincipal) {
        CastingPrincipalDTO dto = new CastingPrincipalDTO();
        dto.setId(castingPrincipal.getIdCasting());
        dto.setFilm(FilmDTO.fromEntity(castingPrincipal.getFilm()));
        dto.setActeur(ActeurDTO.fromEntity(castingPrincipal.getActeur()));
        dto.setCreatedDate(castingPrincipal.getCreatedDate());
        dto.setUpdatedDate(castingPrincipal.getUpdatedDate());
        return dto;
    }

    /**
     * Converts a CastingPrincipalDTO back to a CastingPrincipal entity.
     *
     * @param dto the CastingPrincipalDTO to be converted
     * @return the corresponding CastingPrincipal entity
     */
    public static CastingPrincipal toEntity(CastingPrincipalDTO dto) {
        CastingPrincipal castingPrincipal = new CastingPrincipal();
        castingPrincipal.setIdCasting(dto.getId());
        castingPrincipal.setFilm(dto.getFilm().toEntity());
        castingPrincipal.setActeur(dto.getActeur().toEntity());
        castingPrincipal.setCreatedDate(dto.getCreatedDate());
        castingPrincipal.setUpdatedDate(dto.getUpdatedDate());

        return castingPrincipal;
    }
}
