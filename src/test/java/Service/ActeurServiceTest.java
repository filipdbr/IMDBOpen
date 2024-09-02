package Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import Entities.Business.Personne.Acteur;
import Entities.Business.Personne.Personne;
import Persistence.Repository.IActeurRepository;
import Persistence.Repository.IPersonneRepository;
import Web.Model.DTO.ActeurDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class ActeurServiceTest {

    @InjectMocks
    private ActeurService acteurService;

    @Mock
    private IActeurRepository acteurRepository;

    @Mock
    private IPersonneRepository personneRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        Acteur acteur = new Acteur();
        Personne personne = new Personne();
        personne.setIdentite("John Doe");
        personne.setDateNaissance("1990-01-01");
        personne.setLieuNaissance("Paris, France");
        acteur.setIdImdb("nm0000001");
        personne.setUrl("http://example.com/johndoe");
        acteur.setPersonne(personne);
        when(personneRepository.save(any(Personne.class))).thenReturn(personne);
        when(acteurRepository.saveAndFlush(any(Acteur.class))).thenReturn(acteur);

        ActeurDTO acteurDTO = new ActeurDTO();
        acteurDTO.setIdentite("John Doe");
        acteurDTO.setDateNaissance("1990-01-01");

        acteurService.save(acteurDTO.toEntity()); // Adjusted to match the return type of the save method
        verify(personneRepository, times(1)).save(any(Personne.class));
        verify(acteurRepository, times(1)).saveAndFlush(any(Acteur.class));
    }

    @Test
    void testCreateActeur() {
        Acteur acteur = new Acteur();
        Personne personne = new Personne();
        personne.setIdentite("John Doe");
        personne.setDateNaissance("1990-01-01");
        personne.setLieuNaissance("Paris, France");
        acteur.setIdImdb("nm0000001");
        personne.setUrl("http://example.com/johndoe");
        acteur.setPersonne(personne);
        acteur.setId(1L);
        when(personneRepository.save(any(Personne.class))).thenReturn(personne);
        when(acteurRepository.saveAndFlush(any(Acteur.class))).thenReturn(acteur);

        ActeurDTO acteurDTO = new ActeurDTO();
        acteurDTO.setIdentite("John Doe");
        acteurDTO.setDateNaissance("1990-01-01");

        ActeurDTO result = acteurService.createActeur(acteurDTO);
        assertNotNull(result);
        assertEquals("John Doe", result.getIdentite());
        verify(personneRepository, times(1)).save(any(Personne.class));
        verify(acteurRepository, times(1)).saveAndFlush(any(Acteur.class));
    }

    @Test
    void testUpdateActeur() {
        Acteur acteur = new Acteur();
        Personne personne = new Personne();
        personne.setIdentite("John Doe");
        personne.setDateNaissance("1990-01-01");
        personne.setLieuNaissance("Paris, France");
        acteur.setIdImdb("nm0000001");
        personne.setUrl("http://example.com/johndoe");
        acteur.setPersonne(personne);
        acteur.setId(1L);
        when(personneRepository.save(any(Personne.class))).thenReturn(personne);
        when(acteurRepository.findById(1L)).thenReturn(Optional.of(acteur));
        when(acteurRepository.saveAndFlush(any(Acteur.class))).thenReturn(acteur);

        ActeurDTO acteurDTO = new ActeurDTO();
        acteurDTO.setTaille("1,80m");
        acteurDTO.setIdImdb("nm0000001");

        ActeurDTO result = acteurService.updateActeur(1L, acteurDTO);
        assertNotNull(result);
        assertEquals("1,80m", result.getTaille());
        verify(personneRepository, times(1)).save(any(Personne.class));
        verify(acteurRepository, times(1)).saveAndFlush(any(Acteur.class));
    }

    @Test
    void testDeleteActeur() {
        when(acteurRepository.existsById(1L)).thenReturn(true);

        acteurService.deleteActeur(1L);
        verify(acteurRepository, times(1)).deleteById(1L);
    }
}