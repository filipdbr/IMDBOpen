package Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import Entities.Business.Personne.Personne;
import Entities.Business.Personne.Realisateur;
import Persistence.Repository.IPersonneRepository;
import Persistence.Repository.IRealisateurRepository;
import Web.Model.DTO.RealisateurDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RealisateurServiceTest {

    @InjectMocks
    private RealisateurService realisateurService;

    @Mock
    private IRealisateurRepository realisateurRepository;

    @Mock
    private IPersonneRepository personneRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Realisateur realisateur = new Realisateur();
        when(realisateurRepository.findAll()).thenReturn(Stream.of(realisateur).collect(Collectors.toList()));

        List<RealisateurDTO> result = realisateurService.findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(realisateurRepository, times(1)).findAll();
    }

    @Test
    void testFindByIdImdb() {
        Realisateur realisateur = new Realisateur();
        realisateur.setIdImdb("nm0000001");
        when(realisateurRepository.findByIdImdb("nm0000001")).thenReturn(Stream.of(realisateur).collect(Collectors.toList()));

        List<RealisateurDTO> result = realisateurService.findByIdImdb("nm0000001");
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(realisateurRepository, times(1)).findByIdImdb("nm0000001");
    }

    @Test
    void testSave() {
        Realisateur realisateur = new Realisateur();
        Personne personne = new Personne();
        personne.setIdentite("John Doe");
        personne.setDateNaissance("1990-01-01");
        personne.setLieuNaissance("Paris, France");
        personne.setUrl("http://example.com/johndoe");
        realisateur.setPersonne(personne);
        realisateur.setIdImdb("nm0000001");
        RealisateurDTO realisateurDTO = RealisateurDTO.fromEntity(realisateur);
        when(personneRepository.save(any(Personne.class))).thenReturn(personne);
        when(realisateurRepository.save(any(Realisateur.class))).thenReturn(realisateur);

        RealisateurDTO result = realisateurService.save(realisateurDTO);
        assertNotNull(result);
        assertEquals(realisateurDTO, result);
        verify(personneRepository, times(1)).save(any(Personne.class));
        verify(realisateurRepository, times(1)).save(any(Realisateur.class));
    }

    @Test
    void testUpdate() {
        Realisateur realisateur = new Realisateur();
        Personne personne = new Personne();
        personne.setIdentite("John Doe");
        personne.setDateNaissance("1990-01-01");
        personne.setLieuNaissance("Paris, France");
        realisateur.setIdImdb("nm0000001");
        personne.setUrl("http://example.com/johndoe");
        realisateur.setPersonne(personne);
        realisateur.setId(1L);
        when(realisateurRepository.existsById(1L)).thenReturn(true);
        when(realisateurRepository.save(any(Realisateur.class))).thenReturn(realisateur);
        when(personneRepository.save(any(Personne.class))).thenReturn(personne);

        RealisateurDTO realisateurDTO = new RealisateurDTO();
        realisateurDTO.setId(1L);
        realisateurDTO.setIdentite("Updated Identite");

        RealisateurDTO result = realisateurService.update(realisateurDTO);
        assertNotNull(result);
        assertEquals("Updated Identite", result.getIdentite());
        verify(personneRepository, times(1)).save(any(Personne.class));
        verify(realisateurRepository, times(1)).save(any(Realisateur.class));
    }

    @Test
    void testDeleteById() {
        when(realisateurRepository.existsById(1L)).thenReturn(true);

        realisateurService.deleteById(1L);
        verify(realisateurRepository, times(1)).deleteById(1L);
    }
}