package dev.cadebe.petsapi;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Tag("integration")
@DisplayName("Test PetsApiApplication (IT)")
@SpringBootTest
class PetsApiApplicationTests {

    @Test
    @DisplayName("Test contextLoads")
    void contextLoads() {
    }

}
