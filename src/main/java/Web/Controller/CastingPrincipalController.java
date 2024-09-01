package Web.Controller;

import Entities.Business.CastingPrincipal.CastingPrincipal;
import Entities.Business.Film.Film;
import Entities.Business.Personne.Acteur;
import Exceptions.EntityNotFoundException;
import Exceptions.InvalidDataException;
import Service.CastingPrincipalService;
import Web.Model.DTO.CastingPrincipalDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/casting-principals")
public class CastingPrincipalController {

    private final CastingPrincipalService castingPrincipalService;

    @Autowired
    public CastingPrincipalController(CastingPrincipalService castingPrincipalService) {
        this.castingPrincipalService = castingPrincipalService;
    }

    /**
     * Create a new CastingPrincipal entity.
     * @param castingPrincipal The CastingPrincipal entity to create.
     * @return The created CastingPrincipal entity.
     */
    @PostMapping
    public ResponseEntity<CastingPrincipal> createCastingPrincipal(@RequestBody CastingPrincipal castingPrincipal) {
        try {
            castingPrincipalService.createCasting(CastingPrincipalDTO.fromEntity(castingPrincipal));
            return ResponseEntity.status(HttpStatus.CREATED).body(castingPrincipal);
        } catch (InvalidDataException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Update an existing CastingPrincipal entity.
     * @param id The ID of the CastingPrincipal entity to update.
     * @param updatedCasting The updated CastingPrincipal entity.
     * @return The updated CastingPrincipal entity.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CastingPrincipal> updateCastingPrincipal(@PathVariable Long id, @RequestBody CastingPrincipal updatedCasting) {
        try {
            castingPrincipalService.updateCasting(id, CastingPrincipalDTO.fromEntity(updatedCasting));
            return ResponseEntity.ok(updatedCasting);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (InvalidDataException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Get a CastingPrincipal entity by film and actor.
     *
     * @param filmId   The ID of the film.
     * @param acteurId The ID of the actor.
     * @return The CastingPrincipal entity.
     */
    @GetMapping("/film/{filmId}/actor/{acteurId}")
    public ResponseEntity<CastingPrincipalDTO> getCastingPrincipalByFilmAndActor(@PathVariable Long filmId, @PathVariable Long acteurId) {
        try {
            Film film = new Film();
            film.setId(filmId);
            Acteur acteur = new Acteur();
            acteur.setId(acteurId);
            CastingPrincipalDTO castingPrincipal = castingPrincipalService.getCastingByFilmAndActeur(film, acteur);
            return ResponseEntity.ok(castingPrincipal);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (InvalidDataException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Delete a CastingPrincipal entity by film and actor.
     * @param filmId The ID of the film.
     * @param acteurId The ID of the actor.
     * @return Response indicating success or failure.
     */
    @DeleteMapping("/film/{filmId}/actor/{acteurId}")
    public ResponseEntity<Void> deleteCastingPrincipalByFilmAndActor(@PathVariable Long filmId, @PathVariable Long acteurId) {
        try {
            Film film = new Film();
            film.setId(filmId);
            Acteur acteur = new Acteur();
            acteur.setId(acteurId);
            castingPrincipalService.deleteCastingByFilmAndActeur(film, acteur);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (InvalidDataException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get all CastingPrincipal entities created after a specific date.
     *
     * @param date The date after which CastingPrincipal entities were created.
     * @return A list of CastingPrincipal entities.
     */
    @GetMapping("/created-after")
    public ResponseEntity<List<CastingPrincipalDTO>> getCastingPrincipalsCreatedAfter(@RequestParam("date") LocalDateTime date) {
        try {
            List<CastingPrincipalDTO> castingPrincipals = castingPrincipalService.getCastingByCreatedDate(date);
            return ResponseEntity.ok(castingPrincipals);
        } catch (InvalidDataException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Get all CastingPrincipal entities updated before a specific date.
     *
     * @param date The date before which CastingPrincipal entities were updated.
     * @return A list of CastingPrincipal entities.
     */
    @GetMapping("/updated-before")
    public ResponseEntity<List<CastingPrincipalDTO>> getCastingPrincipalsUpdatedBefore(@RequestParam("date") LocalDateTime date) {
        try {
            List<CastingPrincipalDTO> castingPrincipals = castingPrincipalService.getCastingByUpdatedDate(date);
            return ResponseEntity.ok(castingPrincipals);
        } catch (InvalidDataException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

