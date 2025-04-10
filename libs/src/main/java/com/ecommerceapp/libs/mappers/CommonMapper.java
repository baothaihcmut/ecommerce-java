package com.ecommerceapp.libs.mappers;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CommonMapper {
    @Named("mapUUIDToString")
    default String mapUUIDToString(UUID id) {
        if (id == null) {
            return null;
        }
        return id.toString();
    }

    @Named("mapStringToUUID")
    default UUID mapStringToUUID(String id) {
        if (id == null) {
            return null;
        }
        return UUID.fromString(id);
    }
}
