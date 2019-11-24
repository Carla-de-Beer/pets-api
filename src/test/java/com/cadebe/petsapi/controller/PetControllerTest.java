package com.cadebe.petsapi.controller;

import com.cadebe.petsapi.api.v1.model.PetDTO;
import com.cadebe.petsapi.controller.v1.PetController;
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
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Tag("controller")
@DisplayName("Test IndexController")
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

        verify(petService, times(1)).getAllPets();
        verifyNoMoreInteractions(petService);
    }

    @Test
    void getAllPetsByName() {
    }

    @Test
    void getAllPetsBySpecies() {
    }

    @Test
    void getAllPetsByBreed() {
    }

    @Test
    void getPetById() {
    }

    @Test
    void createNewPet() {
    }

    @Test
    void updateExistingPet() {

    }

    @Test
    void deleteAllPets() {
    }

    @Test
    void deletePetById() {
    }

    private String getPetsURL() {
        return PetController.BASE_URL + "/";
    }
}