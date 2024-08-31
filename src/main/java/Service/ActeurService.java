package Service;

import Entities.Business.Personne.Acteur;
import Entities.Business.Personne.Personne;
import Exceptions.EntityNotFoundException;
import Exceptions.InvalidDataException;
import Persistence.Repository.IActeurRepository;
import Persistence.Repository.IPersonneRepository;
import Web.Model.DTO.ActeurDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActeurService {

    private final IActeurRepository acteurRepository;
    private final IPersonneRepository personneRepository;

    @Autowired
    public ActeurService(IActeurRepository acteurRepository, IPersonneRepository personneRepository) {
        this.acteurRepository = acteurRepository;
        this.personneRepository = personneRepository;
    }

    // Convert Entity to DTO
    private ActeurDTO convertToDTO(Acteur acteur) {
        return ActeurDTO.fromEntity(acteur);
    }

    // Convert DTO to Entity
    private Acteur convertToEntity(ActeurDTO acteurDTO) {
        return acteurDTO.toEntity();
    }

    public void save(Acteur acteur) {
        acteurRepository.saveAndFlush(acteur);
    }

    public void savePersonne(Personne personne){

     personneRepository.saveAndFlush(personne)   ;
    }



    // Method to create a new acteur
    public ActeurDTO createActeur(ActeurDTO acteurDTO) {
        if (acteurDTO == null || acteurDTO.getIdentite() == null || acteurDTO.getDateNaissance() == null) {
            throw new InvalidDataException("Invalid acteur data");
        }
        Acteur acteur = convertToEntity(acteurDTO);
        Acteur savedActeur = acteurRepository.save(acteur);
        return convertToDTO(savedActeur);
    }

    // Method to update an existing acteur
    public ActeurDTO updateActeur(Long id, ActeurDTO acteurDTO) {
        Optional<Acteur> existingActeurOpt = acteurRepository.findById(id);
        if (!existingActeurOpt.isPresent()) {
            throw new EntityNotFoundException("Acteur not found with id: " + id);
        }
        Acteur existingActeur = existingActeurOpt.get();
        existingActeur.setTaille(acteurDTO.getTaille());
        existingActeur.setIdImdb(acteurDTO.getIdImdb());
        Acteur updatedActeur = acteurRepository.save(existingActeur);
        return convertToDTO(updatedActeur);
    }

    // Method to delete an acteur
    public void deleteActeur(Long id) {
        if (!acteurRepository.existsById(id)) {
            throw new EntityNotFoundException("Acteur not found with id: " + id);
        }
        acteurRepository.deleteById(id);
    }



}
