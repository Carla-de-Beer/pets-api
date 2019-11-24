package com.cadebe.petsapi.service;

import com.cadebe.petsapi.api.v1.model.PetDTO;

import java.util.List;

public interface PetService {

    List<PetDTO> getAllPets();

    List<PetDTO> getAllPetsByName(String name);

    List<PetDTO> getAllPetsBySpecies(String species);

    List<PetDTO> getAllPetsByBreed(String breed);

    PetDTO getPetById(String id);

    PetDTO createNewPet(PetDTO pet);

    PetDTO updateExistingPet(String id, PetDTO pet);

    void deleteAllPets();

    void deletePetById(String id);
}
