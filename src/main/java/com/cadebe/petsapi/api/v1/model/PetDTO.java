package com.cadebe.petsapi.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetDTO {

    private ObjectId objectId;
    private String id;
    private String name;
    private String species;
    private String breed;
}
