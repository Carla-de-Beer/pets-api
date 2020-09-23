package dev.cadebe.petsapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

abstract class AbstractRestControllerTest {

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
