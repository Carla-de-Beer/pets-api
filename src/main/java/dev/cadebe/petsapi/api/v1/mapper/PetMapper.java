package dev.cadebe.petsapi.api.v1.mapper;

import dev.cadebe.petsapi.api.v1.model.PetDTO;
import dev.cadebe.petsapi.domain.Pet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PetMapper {

    PetMapper INSTANCE = Mappers.getMapper(PetMapper.class);

    @Mapping(target = "id", expression = "java(source.getObjectId().toHexString())")
    PetDTO petToPetDTOMapper(Pet source);

    Pet petDTOToPetMapper(PetDTO source);
}
