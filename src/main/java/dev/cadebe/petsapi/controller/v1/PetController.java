package dev.cadebe.petsapi.controller.v1;

import dev.cadebe.petsapi.api.v1.model.PetDTO;
import dev.cadebe.petsapi.service.PetService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({PetController.BASE_URL, PetController.BASE_URL + "/",
        PetController.BASE_URL + "/index", PetController.BASE_URL + "/index/"})
public class PetController {

    public static final String BASE_URL = "/api/v1/pets";

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PetDTO> getAllPets() {
        return petService.getAllPets();
    }

    @GetMapping("/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public List<PetDTO> getAllPetsByName(@PathVariable String name) {
        return petService.getAllPetsByName(name);
    }

    @GetMapping("/species/{species}")
    @ResponseStatus(HttpStatus.OK)
    public List<PetDTO> getAllPetsBySpecies(@PathVariable String species) {
        return petService.getAllPetsBySpecies(species);
    }

    @GetMapping("/breed/{breed}")
    @ResponseStatus(HttpStatus.OK)
    public List<PetDTO> getAllPetsByBreed(@PathVariable String breed) {
        return petService.getAllPetsByBreed(breed);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PetDTO getPetById(@PathVariable String id) {
        return petService.getPetById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PetDTO createNewPet(@RequestBody PetDTO pet) {
        return petService.createNewPet(pet);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public PetDTO updateExistingPet(@PathVariable String id, @RequestBody PetDTO pet) {
        return petService.updateExistingPet(id, pet);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteAllPets() {
        petService.deleteAllPets();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePetById(@PathVariable String id) {
        petService.deletePetById(id);
    }
}
