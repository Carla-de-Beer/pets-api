package com.cadebe.petsapi.service;

import com.cadebe.petsapi.api.v1.mapper.PetMapper;
import com.cadebe.petsapi.api.v1.model.PetDTO;
import com.cadebe.petsapi.domain.Pet;
import com.cadebe.petsapi.exception.ResourceNotFoundException;
import com.cadebe.petsapi.repository.PetRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public List<PetDTO> getAllPets() {
        List<Pet> petList = new ArrayList<>(petRepository.findAll());

        return petList.stream().map(PetMapper.INSTANCE::petToPetDTOMapper).collect(Collectors.toList());
    }

    @Override
    public List<PetDTO> getAllPetsByName(String name) {
        return petRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(PetMapper.INSTANCE::petToPetDTOMapper)
                .collect(Collectors.toList());
    }

    @Override
    public List<PetDTO> getAllPetsBySpecies(String species) {
        return petRepository.findBySpeciesContainingIgnoreCase(species)
                .stream()
                .map(PetMapper.INSTANCE::petToPetDTOMapper)
                .collect(Collectors.toList());
    }

    @Override
    public List<PetDTO> getAllPetsByBreed(String breed) {
        return petRepository.findByBreedContainingIgnoreCase(breed)
                .stream()
                .map(PetMapper.INSTANCE::petToPetDTOMapper)
                .collect(Collectors.toList());
    }

    @Override
    public PetDTO getPetById(String id) {
        return PetMapper.INSTANCE.petToPetDTOMapper(petRepository.findById(id).orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    public PetDTO createNewPet(PetDTO pet) {
        return persistPet(pet);
    }

    @Override
    public PetDTO updateExistingPet(String id, PetDTO pet) {
        if (petRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException();
        }
        pet.setObjectId(new ObjectId(id));
        return persistPet(pet);
    }

    @Override
    public void deleteAllPets() {
        petRepository.deleteAll();
    }

    @Override
    public void deletePetById(String id) {
        if (petRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException();
        }
        petRepository.deleteById(id);
    }

    private PetDTO persistPet(PetDTO pet) {
        return PetMapper.INSTANCE.petToPetDTOMapper(petRepository.save(PetMapper.INSTANCE.petDTOToPetMapper(pet)));
    }
}
