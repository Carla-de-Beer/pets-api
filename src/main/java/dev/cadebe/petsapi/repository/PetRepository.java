package dev.cadebe.petsapi.repository;

import dev.cadebe.petsapi.domain.Pet;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PetRepository extends MongoRepository<Pet, String> {

    List<Pet> findByNameContainingIgnoreCase(String name);

    List<Pet> findBySpeciesContainingIgnoreCase(String species);

    List<Pet> findByBreedContainingIgnoreCase(String breed);
}
