package com.ecommerceapp.products.core.port.inbound.commands;

import java.util.List;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductCommand {
    private String name;
    private String description;
    private Integer numOfImage;
    private Boolean hasThumbnail;
    private List<ObjectId> categoryIds;
    private String shopId;
    private List<String> variations;
}
