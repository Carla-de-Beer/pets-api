package com.cadebe.petsapi.controller;

import com.cadebe.petsapi.api.v1.model.PetDTO;
import com.cadebe.petsapi.controller.v1.PetController;
import com.cadebe.petsapi.exception.ResourceNotFoundException;
import com.cadebe.petsapi.exception.RestResponseEntityExceptionHandler;
import com.cadebe.petsapi.service.PetService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.cadebe.petsapi.controller.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Tag("controller")
@DisplayName("IndexController")
@ExtendWith(MockitoExtension.class)
class PetControllerTest {

    private static final String ID_1 = "5dda667b642a1a55b2b562e3";
    private static final ObjectId OBJECT_ID_1 = new ObjectId(ID_1);
    private static final String NAME_1 = "Rex";
    private static final String SPECIES_1 = "Dog";
    private static final String BREED_1 = "Terrier";

    private static final String ID_2 = "5dda66f1642a1a55b2b562e2";
    private static final ObjectId OBJECT_ID_2 = new ObjectId(ID_2);
    private static final String NAME_2 = "Fluffy";
    private static final String SPECIES_2 = "Cat";
    private static final String BREED_2 = "Furry";

    @Mock
    PetService petService;

    @InjectMocks
    PetController petController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(petController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("Test find all pets")
    void getAllPets() throws Exception {
        PetDTO petDTO_1 = PetDTO.builder()
                .objectId(OBJECT_ID_1)
                .id(OBJECT_ID_1.toHexString())
                .name(NAME_1)
                .species(SPECIES_1)
                .breed(BREED_1)
                .build();

        PetDTO petDTO_2 = PetDTO.builder()
                .objectId(OBJECT_ID_2)
                .id(OBJECT_ID_2.toHexString())
                .name(NAME_2)
                .species(SPECIES_2)
                .breed(BREED_2)
                .build();

        List<PetDTO> pets = Arrays.asList(petDTO_1, petDTO_2);

        when(petService.getAllPets()).thenReturn(pets);

        mockMvc.perform(get(getPetsURL())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        then(petService).should().getAllPets();

        verify(petService, times(1)).getAllPets();
        verifyNoMoreInteractions(petService);
    }

    @Test
    @DisplayName("Test get all pets by name")
    void getAllPetsByName() throws Exception {
        PetDTO petDTO = PetDTO.builder()
                .objectId(OBJECT_ID_1)
                .id(OBJECT_ID_1.toHexString())
                .name(NAME_1)
                .species(SPECIES_1)
                .breed(BREED_1)
                .build();

        when(petService.getAllPetsByName(anyString())).thenReturn(Collections.singletonList(petDTO));

        mockMvc.perform(get(getPetsURL() + "name/" + NAME_1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        then(petService).should().getAllPetsByName(anyString());

        verify(petService, times(1)).getAllPetsByName(anyString());
        verifyNoMoreInteractions(petService);
    }

    @Test
    @DisplayName("Test get all pets by name (not found)")
    void getAllPetsByNameNotFound() throws Exception {
        when(petService.getAllPetsByName(anyString())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(getPetsURL() + "name/unknown")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        then(petService).should().getAllPetsByName(anyString());

        verify(petService, times(1)).getAllPetsByName(anyString());
        verifyNoMoreInteractions(petService);
    }

    @Test
    @DisplayName("Test get all pets by species")
    void getAllPetsBySpecies() throws Exception {
        PetDTO petDTO = PetDTO.builder()
                .objectId(OBJECT_ID_1)
                .id(OBJECT_ID_1.toHexString())
                .name(NAME_1)
                .species(SPECIES_1)
                .breed(BREED_1)
                .build();

        when(petService.getAllPetsBySpecies(anyString())).thenReturn(Collections.singletonList(petDTO));

        mockMvc.perform(get(getPetsURL() + "species/" + SPECIES_1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        then(petService).should().getAllPetsBySpecies(anyString());

        verify(petService, times(1)).getAllPetsBySpecies(anyString());
        verifyNoMoreInteractions(petService);
    }

    @Test
    @DisplayName("Test get all pets by species (not found)")
    void getAllPetsBySpeciesNotFound() throws Exception {
        when(petService.getAllPetsBySpecies(anyString())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(getPetsURL() + "species/unknown")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        then(petService).should().getAllPetsBySpecies(anyString());

        verify(petService, times(1)).getAllPetsBySpecies(anyString());
        verifyNoMoreInteractions(petService);
    }

    @Test
    @DisplayName("Test get all pets by breed")
    void getAllPetsByBreed() throws Exception {
        PetDTO petDTO = PetDTO.builder()
                .objectId(OBJECT_ID_1)
                .id(OBJECT_ID_1.toHexString())
                .name(NAME_1)
                .species(SPECIES_1)
                .breed(BREED_1)
                .build();

        when(petService.getAllPetsByBreed(anyString())).thenReturn(Collections.singletonList(petDTO));

        mockMvc.perform(get(getPetsURL() + "breed/" + BREED_1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        then(petService).should().getAllPetsByBreed(anyString());

        verify(petService, times(1)).getAllPetsByBreed(anyString());
        verifyNoMoreInteractions(petService);
    }

    @Test
    @DisplayName("Test get all pets by breed (not found)")
    void getAllPetsByBreedNotFound() throws Exception {
        when(petService.getAllPetsByBreed(anyString())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(getPetsURL() + "breed/unknown")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        then(petService).should().getAllPetsByBreed(anyString());

        verify(petService, times(1)).getAllPetsByBreed(anyString());
        verifyNoMoreInteractions(petService);
    }

    @Test
    @DisplayName("Test find pet by id")
    void getPetById() throws Exception {
        PetDTO petDTO = PetDTO.builder()
                .objectId(OBJECT_ID_1)
                .id(OBJECT_ID_1.toHexString())
                .name(NAME_1)
                .species(SPECIES_1)
                .breed(BREED_1)
                .build();

        when(petService.getPetById(anyString())).thenReturn(petDTO);

        mockMvc.perform(get(getPetsURL() + ID_1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME_1)));

        then(petService).should().getPetById(anyString());

        verify(petService, times(1)).getPetById(anyString());
        verifyNoMoreInteractions(petService);
    }

    @Test
    @DisplayName("Test get pet by id (not found)")
    void getCityByIdNotFound() throws Exception {
        when(petService.getPetById(anyString())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(PetController.BASE_URL + "/a1a1a1a1-a1a1-a1a1-a1a1-a1a1a1a1a1a")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        then(petService).should().getPetById(anyString());

        verify(petService, times(1)).getPetById(anyString());
        verifyNoMoreInteractions(petService);
    }

    @Test
    @DisplayName("Test create new pet")
    void createNewPet() throws Exception {
        PetDTO petDTO = PetDTO.builder()
                .objectId(OBJECT_ID_1)
                .id(OBJECT_ID_1.toHexString())
                .name(NAME_1)
                .species(SPECIES_1)
                .breed(BREED_1)
                .build();

        given(petService.createNewPet(any(PetDTO.class))).willReturn(petDTO);

        mockMvc.perform(post(getPetsURL())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(petDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(petDTO.getName())));

        then(petService).should().createNewPet(any(PetDTO.class));

        verify(petService, times(1)).createNewPet(any(PetDTO.class));
        verifyNoMoreInteractions(petService);
    }

    @Test
    @DisplayName("Test update existing pet")
    void updateExistingPet() throws Exception {
        PetDTO petDTO = PetDTO.builder()
                .objectId(OBJECT_ID_1)
                .id(OBJECT_ID_1.toHexString())
                .name(NAME_1)
                .species(SPECIES_1)
                .breed(BREED_1)
                .build();

        given(petService.updateExistingPet(anyString(), any(PetDTO.class))).willReturn(petDTO);

        mockMvc.perform(put(getPetsURL() + ID_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(petDTO)))
                .andExpect(status().isNoContent());

        then(petService).should().updateExistingPet(anyString(), any(PetDTO.class));

        verify(petService, times(1)).updateExistingPet(anyString(), any(PetDTO.class));
        verifyNoMoreInteractions(petService);
    }

    @Test
    @DisplayName("Test delete all pets")
    void deleteAllPets() throws Exception {
        mockMvc.perform(delete(getPetsURL()))
                .andExpect(status().isOk());

        then(petService).should().deleteAllPets();

        verify(petService, times(1)).deleteAllPets();
        verifyNoMoreInteractions(petService);
    }

    @Test
    @DisplayName("Test delete city by id")
    void deletePetById() throws Exception {
        mockMvc.perform(delete(getPetsURL() + ID_1))
                .andExpect(status().isOk());

        then(petService).should().deletePetById(anyString());

        verify(petService, times(1)).deletePetById(anyString());
        verifyNoMoreInteractions(petService);
    }

    private String getPetsURL() {
        return PetController.BASE_URL + "/";
    }
}