package com.cadebe.petsapi.service;

import com.cadebe.petsapi.api.v1.model.PetDTO;
import com.cadebe.petsapi.domain.Pet;
import com.cadebe.petsapi.exception.ResourceNotFoundException;
import com.cadebe.petsapi.repository.PetRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@Tag("service")
@DisplayName("PetServiceImpl")
@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class PetServiceImplTest {

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
    private PetRepository petRepository;

    private PetServiceImpl petService;

    private Pet pet1;
    private Pet pet2;

    @BeforeEach
    void setUp() {
        petService = new PetServiceImpl(petRepository);

        pet1 = Pet.builder()
                .objectId(OBJECT_ID_1)
                .name(NAME_1)
                .species(SPECIES_1)
                .breed(BREED_1)
                .build();

        pet2 = Pet.builder()
                .objectId(OBJECT_ID_2)
                .name(NAME_2)
                .species(SPECIES_2)
                .breed(BREED_2)
                .build();
    }

    @Test
    @DisplayName("Test find all pets")
    void getAllPets() {
        List<Pet> list = Arrays.asList(pet1, pet2);

        when(petRepository.findAll()).thenReturn(list);

        List<PetDTO> result = petService.getAllPets();

        then(petRepository).should().findAll();

        assertThat(result.size())
                .withFailMessage("Could not find list of pets")
                .isEqualTo(2);

        verify(petRepository, times(1)).findAll();
        verifyNoMoreInteractions(petRepository);
    }

    @Test
    @DisplayName("Test find all pets by name")
    void getAllPetsByName() {
        when(petRepository.findByNameContainingIgnoreCase(anyString())).thenReturn(Collections.singletonList(pet1));

        List<PetDTO> list = petService.getAllPetsByName(NAME_1);

        then(petRepository).should().findByNameContainingIgnoreCase(anyString());

        assertThat(list.size())
                .withFailMessage("Could not find correct pet list for the with given name")
                .isEqualTo(1);

        assertThat(list.get(0).getName())
                .withFailMessage("Could not find correct pet for given name (name does not match)")
                .isEqualTo(NAME_1);

        assertThat(list.get(0).getBreed())
                .withFailMessage("Could not find correct pet for given name (breed does not match)")
                .isEqualTo(BREED_1);

        assertThat(list.get(0).getSpecies())
                .withFailMessage("Could not find correct pet for given name (species does not match)")
                .isEqualTo(SPECIES_1);

        verify(petRepository, times(1)).findByNameContainingIgnoreCase(anyString());
        verifyNoMoreInteractions(petRepository);
    }

    @Test
    @DisplayName("Test find all pets by species")
    void getAllPetsBySpecies() {
        when(petRepository.findBySpeciesContainingIgnoreCase(anyString())).thenReturn(Collections.singletonList(pet1));

        List<PetDTO> list = petService.getAllPetsBySpecies(SPECIES_1);

        then(petRepository).should().findBySpeciesContainingIgnoreCase(anyString());

        assertThat(list.size())
                .withFailMessage("Could not find correct pet list for given species")
                .isEqualTo(1);

        assertThat(list.get(0).getName())
                .withFailMessage("Could not find correct pet for given species (name does not match)")
                .isEqualTo(NAME_1);

        assertThat(list.get(0).getBreed())
                .withFailMessage("Could not find correct pet for given species (breed does not match)")
                .isEqualTo(BREED_1);

        assertThat(list.get(0).getSpecies())
                .withFailMessage("Could not find correct pet for given species (species does not match)")
                .isEqualTo(SPECIES_1);

        verify(petRepository, times(1)).findBySpeciesContainingIgnoreCase(anyString());
        verifyNoMoreInteractions(petRepository);
    }

    @Test
    @DisplayName("Test find all pets by species (not found)")
    void getAllPetsBySpeciesNotFound() {
        when(petRepository.findBySpeciesContainingIgnoreCase(anyString())).thenReturn(Collections.emptyList());

        List<PetDTO> list = petService.getAllPetsBySpecies("unknown");

        then(petRepository).should().findBySpeciesContainingIgnoreCase(anyString());

        assertThat(list.size())
                .withFailMessage("Could not find correct pet list for given species (empty)")
                .isEqualTo(0);

        verify(petRepository, times(1)).findBySpeciesContainingIgnoreCase(anyString());
        verifyNoMoreInteractions(petRepository);
    }

    @Test
    @DisplayName("Test find all pets by breed")
    void getAllPetsByBreed() {
        when(petRepository.findByBreedContainingIgnoreCase(anyString())).thenReturn(Collections.singletonList(pet1));

        List<PetDTO> list = petService.getAllPetsByBreed(BREED_1);

        then(petRepository).should().findByBreedContainingIgnoreCase(anyString());

        assertThat(list.size())
                .withFailMessage("Could not find correct pet list for given breed")
                .isEqualTo(1);

        assertThat(list.get(0).getName())
                .withFailMessage("Could not find correct pet for given breed (name does not match)")
                .isEqualTo(NAME_1);

        assertThat(list.get(0).getBreed())
                .withFailMessage("Could not find correct pet for given breed (breed does not match)")
                .isEqualTo(BREED_1);

        assertThat(list.get(0).getSpecies())
                .withFailMessage("Could not find correct pet for given breed (species does not match)")
                .isEqualTo(SPECIES_1);

        verify(petRepository, times(1)).findByBreedContainingIgnoreCase(anyString());
        verifyNoMoreInteractions(petRepository);
    }

    @Test
    @DisplayName("Test find all pets by breed (not found)")
    void getAllPetsByBreedNotFound() {
        when(petRepository.findByBreedContainingIgnoreCase(anyString())).thenReturn(Collections.emptyList());

        List<PetDTO> list = petService.getAllPetsByBreed("unknown");

        then(petRepository).should().findByBreedContainingIgnoreCase(anyString());

        assertThat(list.size())
                .withFailMessage("Could not find correct pet list for given breed (empty)")
                .isEqualTo(0);

        verify(petRepository, times(1)).findByBreedContainingIgnoreCase(anyString());
        verifyNoMoreInteractions(petRepository);
    }

    @Test
    @DisplayName("Test find pet by id")
    void getPetById() {
        when(petRepository.findById(anyString())).thenReturn(java.util.Optional.ofNullable(pet1));

        PetDTO foundPet = petService.getPetById(ID_1);

        then(petRepository).should().findById(anyString());

        assertThat(foundPet.getName())
                .withFailMessage("Could not find pet with given id")
                .isEqualTo(NAME_1);

        verify(petRepository, times(1)).findById(anyString());
        verifyNoMoreInteractions(petRepository);
    }

    @Test
    @DisplayName("Test find pet by id (not found)")
    void getPetByIdNotFound() {
        when(petRepository.findById(anyString())).thenThrow(new ResourceNotFoundException());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> petRepository.findById("a1a1a1a1-a1a1-a1a1-a1a1-a1a1a1a1a1a")
                .orElseThrow(ResourceNotFoundException::new));

        verify(petRepository, times(1)).findById(anyString());
        verifyNoMoreInteractions(petRepository);
    }

    @Test
    @DisplayName("Test save new pet")
    void createNewPet() {
        PetDTO petDTO = PetDTO.builder()
                .objectId(OBJECT_ID_1)
                .id(OBJECT_ID_1.toHexString())
                .name(NAME_1)
                .species(SPECIES_1)
                .breed(BREED_1)
                .build();

        when(petRepository.save(any(Pet.class))).thenReturn(pet1);

        PetDTO savedVendorDTO = petService.createNewPet(petDTO);

        // 'should' defaults to times = 1
        then(petRepository).should().save(any(Pet.class));

        assertThat(savedVendorDTO.getName())
                .withFailMessage("Could not correctly save city")
                .contains(NAME_1);

        verify(petRepository, times(1)).save(any(Pet.class));
        verifyNoMoreInteractions(petRepository);
    }

    @Test
    @DisplayName("Test update existing pet")
    @Disabled
    void updateExistingPet() {
        PetDTO petDTO = PetDTO.builder()
                .objectId(OBJECT_ID_1)
                .id(OBJECT_ID_1.toHexString())
                .name(NAME_1)
                .species("newSpecies")
                .breed("newBreed")
                .build();

        Pet pet = Pet.builder()
                .objectId(petDTO.getObjectId())
                .name(petDTO.getName())
                .species(petDTO.getSpecies())
                .breed(petDTO.getBreed())
                .build();

        given(petRepository.save(any(Pet.class))).willReturn(pet);

        PetDTO savedPetDTO = petService.updateExistingPet(OBJECT_ID_1.toHexString(), petDTO);

        // 'should' defaults to times = 1
        then(petRepository).should().save(any(Pet.class));

        assertThat(savedPetDTO.getName())
                .withFailMessage("Could not correctly update pet")
                .contains(NAME_1);

        verify(petRepository, times(1)).save(any(Pet.class));
        verifyNoMoreInteractions(petRepository);
    }

    @Test
    @DisplayName("Test delete all pets")
    void deleteAllPets() {
        petService.deleteAllPets();

        then(petRepository).should().deleteAll();

        verify(petRepository, times(1)).deleteAll();
        verifyNoMoreInteractions(petRepository);
    }

    @Test
    @DisplayName("Test delete pet by id")
    void deletePetById() {

    }
}