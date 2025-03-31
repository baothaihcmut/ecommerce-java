package com.ecommerceapp.shops.core.port.inbound.commands;

import java.util.List;

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
    private List<String> categoryIds;
    private String shopId;
    private List<String> variations;
}
