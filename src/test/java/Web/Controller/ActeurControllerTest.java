package Web.Controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import Exceptions.EntityNotFoundException;
import Exceptions.InvalidDataException;
import Service.ActeurService;

import Web.Model.DTO.ActeurDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class ActeurControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ActeurService acteurService;

    @InjectMocks
    private ActeurController acteurController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(acteurController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCreateActeur_Success() throws Exception {
        // Arrange
        ActeurDTO acteurDTO = new ActeurDTO();
        acteurDTO.setId(1L);
        acteurDTO.setIdentite("Test Acteur");
        when(acteurService.createActeur(any(ActeurDTO.class))).thenReturn(acteurDTO);

        // Act & Assert
        mockMvc.perform(post("/api/acteurs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(acteurDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(201))
                .andExpect(jsonPath("$.message").value("Acteur created successfully"))
                .andExpect(jsonPath("$.data.nom").value("Test Acteur"));

        verify(acteurService, times(1)).createActeur(any(ActeurDTO.class));
    }

    @Test
    void testCreateActeur_InvalidData() throws Exception {
        // Arrange
        ActeurDTO acteurDTO = new ActeurDTO();
        doThrow(new InvalidDataException("Invalid data")).when(acteurService).createActeur(any(ActeurDTO.class));

        // Act & Assert
        mockMvc.perform(post("/api/acteurs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(acteurDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Invalid data"));

        verify(acteurService, times(1)).createActeur(any(ActeurDTO.class));
    }

    @Test
    void testUpdateActeur_Success() throws Exception {
        // Arrange
        Long id = 1L;
        ActeurDTO acteurDTO = new ActeurDTO();
        acteurDTO.setId(id);
        acteurDTO.setIdentite("Updated Acteur");
        when(acteurService.updateActeur(eq(id), any(ActeurDTO.class))).thenReturn(acteurDTO);

        // Act & Assert
        mockMvc.perform(put("/api/acteurs/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(acteurDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Acteur updated successfully"))
                .andExpect(jsonPath("$.data.nom").value("Updated Acteur"));

        verify(acteurService, times(1)).updateActeur(eq(id), any(ActeurDTO.class));
    }

    @Test
    void testUpdateActeur_NotFound() throws Exception {
        // Arrange
        Long id = 1L;
        ActeurDTO acteurDTO = new ActeurDTO();
        doThrow(new EntityNotFoundException("Acteur not found")).when(acteurService).updateActeur(eq(id), any(ActeurDTO.class));

        // Act & Assert
        mockMvc.perform(put("/api/acteurs/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(acteurDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Acteur not found"));

        verify(acteurService, times(1)).updateActeur(eq(id), any(ActeurDTO.class));
    }

    @Test
    void testDeleteActeur_Success() throws Exception {
        // Arrange
        Long id = 1L;
        doNothing().when(acteurService).deleteActeur(id);

        // Act & Assert
        mockMvc.perform(delete("/api/acteurs/{id}", id))
                .andExpect(status().isNoContent());

        verify(acteurService, times(1)).deleteActeur(id);
    }

    @Test
    void testDeleteActeur_NotFound() throws Exception {
        // Arrange
        Long id = 1L;
        doThrow(new EntityNotFoundException("Acteur not found")).when(acteurService).deleteActeur(id);

        // Act & Assert
        mockMvc.perform(delete("/api/acteurs/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Acteur not found"));

        verify(acteurService, times(1)).deleteActeur(id);
    }
}
