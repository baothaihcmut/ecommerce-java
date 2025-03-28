package com.ecommerceapp.products.adapter.transport.rest.dtos.response;

import org.bson.types.ObjectId;

import com.ecommerceapp.libs.serializer.ObjectIdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VariationReponseDTO {
    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId id;
    
    private String name;
}
