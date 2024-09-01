package Service;

import Entities.Business.CastingPrincipal.CastingPrincipal;
import Entities.Business.Film.Film;
import Entities.Business.Personne.Acteur;
import Exceptions.EntityNotFoundException;
import Exceptions.InvalidDataException;
import Exceptions.ServiceException;
import Persistence.Repository.ICastingPrincipalRepository;
import Web.Model.DTO.CastingPrincipalDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CastingPrincipalService {

    private final ICastingPrincipalRepository castingPrincipalRepository;

    @Autowired
    public CastingPrincipalService(ICastingPrincipalRepository castingPrincipalRepository) {
        this.castingPrincipalRepository = castingPrincipalRepository;
    }

    public List<CastingPrincipalDTO> getCastingByFilm(Film film) {
        try {
            if (film == null) {
                throw new InvalidDataException("Film cannot be null");
            }
            return castingPrincipalRepository.findByFilm(String.valueOf(film)).stream()
                    .map(CastingPrincipalDTO::fromEntity)
                    .collect(Collectors.toList());
        } catch (InvalidDataException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Failed to retrieve casting by film");
        }
    }

    public List<CastingPrincipalDTO> getCastingByActeur(Acteur acteur) {
        try {
            if (acteur == null) {
                throw new InvalidDataException("Actor cannot be null");
            }
            return castingPrincipalRepository.findByActeur(String.valueOf(acteur)).stream()
                    .map(CastingPrincipalDTO::fromEntity)
                    .collect(Collectors.toList());
        } catch (InvalidDataException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Failed to retrieve casting by actor");
        }
    }

    public CastingPrincipalDTO getCastingByFilmAndActeur(String film, String acteur) {
        try {
            if (film == null || acteur == null) {
                throw new InvalidDataException("Film and Actor cannot be null");
            }
            CastingPrincipal casting = castingPrincipalRepository.findByFilmAndActeur(film, acteur)
                    .orElseThrow(() -> new EntityNotFoundException("Casting not found for the provided film and actor"));
            return CastingPrincipalDTO.fromEntity(casting);
        } catch (EntityNotFoundException | InvalidDataException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Failed to retrieve casting by film and actor");
        }
    }

    public List<CastingPrincipalDTO> getCastingByCreatedDate(LocalDateTime createdDate) {
        try {
            if (createdDate == null) {
                throw new InvalidDataException("Created date cannot be null");
            }
            return castingPrincipalRepository.findByCreatedDate(createdDate).stream()
                    .map(CastingPrincipalDTO::fromEntity)
                    .collect(Collectors.toList());
        } catch (InvalidDataException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Failed to retrieve casting by created date");
        }
    }

    public List<CastingPrincipalDTO> getCastingByUpdatedDate(LocalDateTime updatedDate) {
        try {
            if (updatedDate == null) {
                throw new InvalidDataException("Updated date cannot be null");
            }
            return castingPrincipalRepository.findByUpdatedDate(updatedDate).stream()
                    .map(CastingPrincipalDTO::fromEntity)
                    .collect(Collectors.toList());
        } catch (InvalidDataException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Failed to retrieve casting by updated date");
        }
    }

    public void createCasting(CastingPrincipalDTO castingPrincipalDTO) {
        try {
            CastingPrincipal castingPrincipal = CastingPrincipalDTO.toEntity(castingPrincipalDTO);
            if (castingPrincipal.getFilm() == null || castingPrincipal.getActeur() == null) {
                throw new InvalidDataException("Film and Actor are required to create a casting");
            }
            castingPrincipal.setCreatedDate(LocalDateTime.now());
            castingPrincipal.setUpdatedDate(LocalDateTime.now());
            castingPrincipalRepository.save(castingPrincipal);
        } catch (InvalidDataException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Failed to create casting");
        }
    }

    public void updateCasting(Long id, CastingPrincipalDTO updatedCastingDTO) {
        try {
            CastingPrincipal updatedCasting = CastingPrincipalDTO.toEntity(updatedCastingDTO);
            if (updatedCasting.getFilm() == null || updatedCasting.getActeur() == null) {
                throw new InvalidDataException("Film and Actor are required to update casting");
            }

            CastingPrincipal existingCasting = castingPrincipalRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Casting not found with id: " + id));

            existingCasting.setFilm(updatedCasting.getFilm());
            existingCasting.setActeur(updatedCasting.getActeur());
            existingCasting.setUpdatedDate(LocalDateTime.now());

            castingPrincipalRepository.save(existingCasting);
        } catch (EntityNotFoundException | InvalidDataException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Failed to update casting");
        }
    }

    public void deleteCastingByFilmAndActeur(String film, String acteur) {
        try {
            if (film == null || acteur == null) {
                throw new InvalidDataException("Film and Actor cannot be null");
            }
            if (!castingPrincipalRepository.existsByFilmAndActeur(film, acteur)) {
                throw new EntityNotFoundException("Casting not found for the provided film and actor");
            }
            castingPrincipalRepository.deleteByFilmAndActeur(film, acteur);
        } catch (EntityNotFoundException | InvalidDataException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Failed to delete casting");
        }
    }
}