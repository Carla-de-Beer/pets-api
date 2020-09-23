package dev.cadebe.petsapi.bootstrap;

import dev.cadebe.petsapi.api.v1.model.PetDTO;
import dev.cadebe.petsapi.service.PetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Bootstrap implements CommandLineRunner {

    private final PetService petService;

    public Bootstrap(PetService petService) {
        this.petService = petService;
    }

    @Override
    public void run(String... args) {
        addPets();
    }

    private void addPets() {

        petService.deleteAllPets();

        PetDTO spot = PetDTO.builder()
                .name("Spot")
                .species("Dog")
                .breed("Dalmation")
                .build();

        PetDTO ziggy = PetDTO.builder()
                .name("Ziggy")
                .species("Dog")
                .breed("Terrier")
                .build();

        PetDTO bandit = PetDTO.builder()
                .name("Bandit")
                .species("Cat")
                .breed("Siamese")
                .build();

        PetDTO henry = PetDTO.builder()
                .name("Henry")
                .species("Tortoise")
                .breed("Leopard Tortoise")
                .build();

        PetDTO cocky = PetDTO.builder()
                .name("Cocky")
                .species("Bird")
                .breed("Cockatoo")
                .build();

        petService.createNewPet(spot);
        petService.createNewPet(henry);
        petService.createNewPet(cocky);
        petService.createNewPet(bandit);
        petService.createNewPet(ziggy);

        log.info("********* Pets loaded: " + petService.getAllPets());
    }
}
