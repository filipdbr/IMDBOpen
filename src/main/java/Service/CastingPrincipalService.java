package Service;

import Entities.Business.CastingPrincipal.CastingPrincipal;
import Entities.Business.Film.Film;
import Entities.Business.Personne.Acteur;
import Exceptions.EntityNotFoundException;
import Exceptions.InvalidDataException;
import Exceptions.ServiceException;
import Persistence.Repository.ICastingPrincipalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CastingPrincipalService {

    private final ICastingPrincipalRepository castingPrincipalRepository;

    @Autowired
    public CastingPrincipalService(ICastingPrincipalRepository castingPrincipalRepository) {
        this.castingPrincipalRepository = castingPrincipalRepository;
    }

    public List<CastingPrincipal> getCastingByFilm(Film film) {
        try {
            if (film == null) {
                throw new InvalidDataException("Film cannot be null");
            }
            return castingPrincipalRepository.findByFilm(film);
        } catch (InvalidDataException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Failed to retrieve casting by film", e);
        }
    }

    public List<CastingPrincipal> getCastingByActeur(Acteur acteur) {
        try {
            if (acteur == null) {
                throw new InvalidDataException("Actor cannot be null");
            }
            return castingPrincipalRepository.findByActeur(acteur);
        } catch (InvalidDataException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Failed to retrieve casting by actor", e);
        }
    }

    public CastingPrincipal getCastingByFilmAndActeur(Film film, Acteur acteur) {
        try {
            if (film == null || acteur == null) {
                throw new InvalidDataException("Film and Actor cannot be null");
            }
            return castingPrincipalRepository.findByFilmAndActeur(film, acteur)
                    .orElseThrow(() -> new EntityNotFoundException("Casting not found for the provided film and actor"));
        } catch (EntityNotFoundException | InvalidDataException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Failed to retrieve casting by film and actor", e);
        }
    }

    public List<CastingPrincipal> getCastingByCreatedDate(LocalDateTime createdDate) {
        try {
            if (createdDate == null) {
                throw new InvalidDataException("Created date cannot be null");
            }
            return castingPrincipalRepository.findByCreatedDate(createdDate);
        } catch (InvalidDataException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Failed to retrieve casting by created date", e);
        }
    }

    public List<CastingPrincipal> getCastingByUpdatedDate(LocalDateTime updatedDate) {
        try {
            if (updatedDate == null) {
                throw new InvalidDataException("Updated date cannot be null");
            }
            return castingPrincipalRepository.findByUpdatedDate(updatedDate);
        } catch (InvalidDataException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Failed to retrieve casting by updated date", e);
        }
    }

    public void createCasting(CastingPrincipal castingPrincipal) {
        try {
            if (castingPrincipal.getFilm() == null || castingPrincipal.getActeur() == null) {
                throw new InvalidDataException("Film and Actor are required to create a casting");
            }
            castingPrincipal.setCreatedDate(LocalDateTime.now());
            castingPrincipal.setUpdatedDate(LocalDateTime.now());
            castingPrincipalRepository.save(castingPrincipal);
        } catch (InvalidDataException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Failed to create casting", e);
        }
    }

    public void updateCasting(Long id, CastingPrincipal updatedCasting) {
        try {
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
            throw new ServiceException("Failed to update casting", e);
        }
    }

    public void deleteCastingByFilmAndActeur(Film film, Acteur acteur) {
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
            throw new ServiceException("Failed to delete casting", e);
        }
    }
}