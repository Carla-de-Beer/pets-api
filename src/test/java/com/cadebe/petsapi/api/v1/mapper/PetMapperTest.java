package com.cadebe.petsapi.api.v1.mapper;

import com.cadebe.petsapi.api.v1.model.PetDTO;
import com.cadebe.petsapi.domain.Pet;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("mapper")
@DisplayName("PetMapper")
class PetMapperTest {

    private final ObjectId ID = new ObjectId("507f1f77bcf86cd799439011");
    private final String BREED = "Terrier";
    private final String SPECIES = "Doggie";
    private final String NAME = "Rex";

    private PetDTO petDTO;
    private Pet pet;

    @BeforeEach
    void setUp() {
        petDTO = PetDTO.builder()
                .objectId(ID)
                .name(NAME)
                .species(SPECIES)
                .breed(BREED)
                .build();

        pet = Pet.builder()
                .objectId(ID)
                .name(NAME)
                .species(SPECIES)
                .breed(BREED)
                .build();
    }

    @Test
    @DisplayName("Test petToPetDTOMapper")
    void petToPetDTOMapper() {
        PetDTO mappedPetDTO = PetMapper.INSTANCE.petToPetDTOMapper(pet);

        assertThat(mappedPetDTO.getObjectId())
                .withFailMessage("Could not correctly map petDTO to pet by ObjectId")
                .isEqualTo(ID);

        assertThat(mappedPetDTO.getName())
                .withFailMessage("Could not correctly map petDTO to pet by name")
                .isEqualTo(NAME);

        assertThat(mappedPetDTO.getSpecies())
                .withFailMessage("Could not correctly map petDTO to pet by species")
                .isEqualTo(SPECIES);

        assertThat(mappedPetDTO.getBreed())
                .withFailMessage("Could not correctly map petDTO to pet by breed")
                .isEqualTo(BREED);
    }

    @Test
    @DisplayName("Test petToPetDTOMapper (null)")
    void petToPetDTOMapperNull() {
        PetDTO mappedPetDTO = PetMapper.INSTANCE.petToPetDTOMapper(null);

        assertThat(mappedPetDTO)
                .withFailMessage("Could not correctly map pet to petDTO (null)")
                .isNull();
    }

    @Test
    @DisplayName("Test petDTOToPetMapper")
    void petDTOToPetMapper() {
        Pet mappedPet = PetMapper.INSTANCE.petDTOToPetMapper(petDTO);

        assertThat(mappedPet.getObjectId())
                .withFailMessage("Could not correctly map petDTO to pet by ObjectId")
                .isEqualTo(ID);

        assertThat(mappedPet.getName())
                .withFailMessage("Could not correctly map petDTO to pet by name")
                .isEqualTo(NAME);

        assertThat(mappedPet.getSpecies())
                .withFailMessage("Could not correctly map petDTO to pet by species")
                .isEqualTo(SPECIES);

        assertThat(mappedPet.getBreed())
                .withFailMessage("Could not correctly map petDTO to pet by breed")
                .isEqualTo(BREED);
    }

    @Test
    @DisplayName("Test petDTOToPetMapper (null)")
    void petDTOToPetMapperNull() {
        Pet mappedPet = PetMapper.INSTANCE.petDTOToPetMapper(null);

        assertThat(mappedPet)
                .withFailMessage("Could not correctly map petDTO to pet (null)")
                .isNull();
    }
}