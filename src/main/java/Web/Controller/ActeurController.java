package Web.Controller;

import Exceptions.EntityNotFoundException;
import Exceptions.InvalidDataException;
import Service.ActeurService;
import Web.Model.DTO.ActeurDTO;
import Web.Model.Generic.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/acteurs")
public class ActeurController {

    @Autowired
    private ActeurService acteurService;

    /*@GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ActeurDTO>>> searchActeurs(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) String dateNaissance,
            @RequestParam(required = false) String sortBy
    ) {
        try {
            List<ActeurDTO> acteurs = acteurService.findActeursWithFiltersAndSorting(nom, dateNaissance, sortBy);
            ApiResponse<List<ActeurDTO>> response = new ApiResponse<>(HttpStatus.OK.value(), "Acteurs retrieved successfully", acteurs);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InvalidDataException ex) {
            throw ex;  // Handled by the Global Exception Handler
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while searching for acteurs", ex);  // Handled by the Global Exception Handler
        }
    }

    @GetMapping("/nom/{nom}")
    public ResponseEntity<ApiResponse<List<ActeurDTO>>> getActeursByNom(@PathVariable String identite) {
        try {
            List<ActeurDTO> acteurs = acteurService.findActeursByIdentite(identite);
            if (acteurs.isEmpty()) {
                throw new EntityNotFoundException("No acteurs found with nom: " + identite);
            }
            ApiResponse<List<ActeurDTO>> response = new ApiResponse<>(HttpStatus.OK.value(), "Acteurs retrieved successfully", acteurs);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            throw ex;  // Handled by the Global Exception Handler
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while retrieving acteurs by nom", ex);  // Handled by the Global Exception Handler
        }
    }*/

    @PostMapping
    public ResponseEntity<ApiResponse<ActeurDTO>> createActeur(@RequestBody ActeurDTO acteurDTO) {
        try {
            ActeurDTO createdActeur = acteurService.createActeur(acteurDTO);
            ApiResponse<ActeurDTO> response = new ApiResponse<>(HttpStatus.CREATED.value(), "Acteur created successfully", createdActeur);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (InvalidDataException ex) {
            throw ex;  // Handled by the Global Exception Handler
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while creating the acteur", ex);  // Handled by the Global Exception Handler
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ActeurDTO>> updateActeur(@PathVariable Long id, @RequestBody ActeurDTO acteurDTO) {
        try {
            ActeurDTO updatedActeur = acteurService.updateActeur(id, acteurDTO);
            ApiResponse<ActeurDTO> response = new ApiResponse<>(HttpStatus.OK.value(), "Acteur updated successfully", updatedActeur);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            throw ex;  // Handled by the Global Exception Handler
        } catch (InvalidDataException ex) {
            throw ex;  // Handled by the Global Exception Handler
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while updating the acteur", ex);  // Handled by the Global Exception Handler
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteActeur(@PathVariable Long id) {
        try {
            acteurService.deleteActeur(id);
            ApiResponse<Void> response = new ApiResponse<>(HttpStatus.NO_CONTENT.value(), "Acteur deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException ex) {
            throw ex;  // Handled by the Global Exception Handler
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while deleting the acteur", ex);  // Handled by the Global Exception Handler
        }
    }
}