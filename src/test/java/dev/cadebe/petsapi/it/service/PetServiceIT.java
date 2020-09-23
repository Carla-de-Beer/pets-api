package dev.cadebe.petsapi.it.service;

import dev.cadebe.petsapi.api.v1.model.PetDTO;
import dev.cadebe.petsapi.bootstrap.Bootstrap;
import dev.cadebe.petsapi.exception.ResourceNotFoundException;
import dev.cadebe.petsapi.repository.PetRepository;
import dev.cadebe.petsapi.service.PetService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Tag("integration")
@DisplayName("PetService (IT)")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class PetServiceIT {

    @Autowired
    PetRepository petRepository;

    @Autowired
    PetService petService;

    @BeforeEach
    void setUp() {
        Bootstrap bs = new Bootstrap(petService);
        bs.run();
    }

    @Test
    @DisplayName("Test get all pets")
    void getAllPets() {
        List<PetDTO> pets = petService.getAllPets();

        assertThat(pets.size())
                .withFailMessage("All of the Bootstrap pets could not be found")
                .isEqualTo(5);
    }

    @Test
    @DisplayName("Test get all pets by name")
    void getAllPetsByName() {
        String name = getFirstPet().getName();
        List<PetDTO> foundPets = petService.getAllPetsByName(name);

        assertNotNull(foundPets);

        assertThat(foundPets.size())
                .withFailMessage("Could not find all pets with given name")
                .isEqualTo(1);
    }

    @Test
    @DisplayName("Test get all pets by species")
    void getAllPetsBySpecies() {
        String species = getFirstPet().getSpecies();
        List<PetDTO> foundPets = petService.getAllPetsBySpecies(species);

        assertNotNull(foundPets);

        assertThat(foundPets.size())
                .withFailMessage("Could not find all pets with given species")
                .isEqualTo(2);
    }

    @Test
    @DisplayName("Test get all pets by breed")
    void getAllPetsByBreed() {
        String breed = getFirstPet().getBreed();
        List<PetDTO> foundPets = petService.getAllPetsByBreed(breed);

        assertNotNull(foundPets);

        assertThat(foundPets.size())
                .withFailMessage("Could not find all pets with given breed")
                .isEqualTo(1);
    }

    @Test
    @DisplayName("Test get pet by id")
    void getPetById() {
        String id = getFirstPetIdValue();
        PetDTO foundPet = petService.getPetById(id);

        assertNotNull(foundPet);
    }

    @Test
    @DisplayName("Test create new pet")
    void createNewPet() {
        String name = "Name x";
        List<PetDTO> petList1 = petService.getAllPets();

        PetDTO petDTO = PetDTO.builder()
                .name(name)
                .species("Species y")
                .breed("Breed z")
                .build();

        PetDTO savedPetDTO = petService.createNewPet(petDTO);

        List<PetDTO> petList2 = petService.getAllPets();

        assertThat(savedPetDTO.getName())
                .withFailMessage("New pet's name has does not match")
                .isEqualTo(name);

        assertThat(petList1.size())
                .withFailMessage("New pet has not been added")
                .isEqualTo(petList2.size() - 1);
    }

    @Test
    @DisplayName("Test update existing pet")
    void updateExistingPet() {
        String updatedName = "sweets";
        String id = getFirstPetIdValue();

        // Get a pet
        PetDTO originalPet = petService.getPetById(id);
        assertNotNull(originalPet);

        String originalName = originalPet.getName();

        PetDTO petDTO = PetDTO.builder()
                .name(updatedName)
                .build();

        // update it
        petService.updateExistingPet(id, petDTO);

        // check the update
        PetDTO updatedPet = petService.getPetById(id);

        assertNotNull(updatedPet);

        assertEquals(updatedName, updatedPet.getName(), () -> "Updated pet's new name does not match");

        assertThat(originalName)
                .withFailMessage("Updated pet's new name has not been updated")
                .isNotEqualTo(updatedPet.getName());
    }

    @Test
    @DisplayName("Test delete all pets")
    void deleteAllPets() {
        List<PetDTO> petList = petService.getAllPets();

        petService.deleteAllPets();

        List<PetDTO> emptyPetList = petService.getAllPets();

        assertThat(emptyPetList.size())
                .withFailMessage("Size of deleted list is not zero")
                .isZero();

        assertThat(petList.size())
                .withFailMessage("Size of the two lists is not equal")
                .isNotEqualTo(emptyPetList.size());
    }

    @Test
    @DisplayName("Test delete pet by id")
    void deletePetById() {
        List<PetDTO> petList1 = petService.getAllPets();

        String id = getFirstPetIdValue();
        petService.getPetById(id);

        petService.deletePetById(id);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            petService.getPetById(id);
        });

        List<PetDTO> petList2 = petService.getAllPets();

        assertThat(petList1.size() - 1)
                .withFailMessage("Pet has not been deleted")
                .isEqualTo(petList2.size());
    }

    private String getFirstPetIdValue() {
        return getFirstPet().getId();
    }

    private PetDTO getFirstPet() {
        return petService.getAllPets().get(0);
    }
}