package Service;

import Entities.Business.Personne.Acteur;
import Exceptions.EntityNotFoundException;
import Exceptions.InvalidDataException;
import Persistence.Repository.IActeurRepository;
import Web.Model.DTO.ActeurDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActeurService {

    private final IActeurRepository acteurRepository;

    @Autowired
    public ActeurService(IActeurRepository acteurRepository) {
        this.acteurRepository = acteurRepository;
    }

    // Convert Entity to DTO
    private ActeurDTO convertToDTO(Acteur acteur) {
        return ActeurDTO.fromEntity(acteur);
    }

    // Convert DTO to Entity
    private Acteur convertToEntity(ActeurDTO acteurDTO) {
        return acteurDTO.toEntity();
    }

    public List<ActeurDTO> findActeursWithFiltersAndSorting(String nom, String dateNaissance, String sortBy) {
        List<Acteur> acteurs = acteurRepository.findAll();
        return acteurs.stream()
                .filter(acteur -> (nom == null || acteur.getNom().equalsIgnoreCase(nom)) &&
                        (dateNaissance == null || acteur.getDateNaissance().toString().equals(dateNaissance)))
                .sorted((a1, a2) -> {
                    if (sortBy == null) return 0;
                    switch (sortBy) {
                        case "nom":
                            return a1.getNom().compareToIgnoreCase(a2.getNom());
                        case "dateNaissance":
                            return a1.getDateNaissance().compareTo(a2.getDateNaissance());
                        default:
                            return 0;
                    }
                })
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ActeurDTO> findActeursByNom(String nom) {
        List<Acteur> acteurs = acteurRepository.findByNom(nom);
        if (acteurs.isEmpty()) {
            throw new EntityNotFoundException("No acteurs found with nom: " + nom);
        }
        return acteurs.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public ActeurDTO createActeur(ActeurDTO acteurDTO) {
        if (acteurDTO == null || acteurDTO.getNom() == null) {
            throw new InvalidDataException("Invalid acteur data");
        }
        Acteur acteur = convertToEntity(acteurDTO);
        Acteur savedActeur = acteurRepository.save(acteur);
        return convertToDTO(savedActeur);
    }

    public ActeurDTO updateActeur(Long id, ActeurDTO acteurDTO) {
        Optional<Acteur> existingActeurOpt = acteurRepository.findById(id);
        if (!existingActeurOpt.isPresent()) {
            throw new EntityNotFoundException("Acteur not found with id: " + id);
        }
        Acteur existingActeur = existingActeurOpt.get();
        existingActeur.setNom(acteurDTO.getNom());
        existingActeur.setDateNaissance(acteurDTO.getDateNaissance());
        Acteur updatedActeur = acteurRepository.save(existingActeur);
        return convertToDTO(updatedActeur);
    }

    public void deleteActeur(Long id) {
        if (!acteurRepository.existsById(id)) {
            throw new EntityNotFoundException("Acteur not found with id: " + id);
        }
        acteurRepository.deleteById(id);
    }
}