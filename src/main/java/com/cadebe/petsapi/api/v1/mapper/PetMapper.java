package com.cadebe.petsapi.api.v1.mapper;

import com.cadebe.petsapi.api.v1.model.PetDTO;
import com.cadebe.petsapi.domain.Pet;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class PetMapper {

    public PetDTO petToPetDTOMapper(Pet source) {
        if (source == null) {
            return null;
        }
        return PetDTO.builder()
                .objectId(source.getObjectId())
                .id(convertObjectIdToHexString(source.getObjectId()))
                .name(source.getName())
                .species(source.getSpecies())
                .breed(source.getBreed())
                .build();
    }

    public Pet petDTOToPetMapper(PetDTO source) {
        if (source == null) {
            return null;
        }
        return Pet.builder()
                .objectId(source.getObjectId())
                .name(source.getName())
                .species(source.getSpecies())
                .breed(source.getBreed())
                .build();
    }

    private static String convertObjectIdToHexString(ObjectId objectId) {
        return objectId.toHexString();
    }
}
