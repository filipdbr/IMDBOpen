package Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import Entities.Business.Film.Film;
import Persistence.Repository.IFilmRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class FilmServiceTest {

    @InjectMocks
    private FilmService filmService;

    @Mock
    private IFilmRepository filmRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        Film film = new Film();
        when(filmRepository.saveAndFlush(film)).thenReturn(film);

        filmService.save(film); // Adjusted to match the return type of the save method
        verify(filmRepository, times(1)).saveAndFlush(film);
    }
}