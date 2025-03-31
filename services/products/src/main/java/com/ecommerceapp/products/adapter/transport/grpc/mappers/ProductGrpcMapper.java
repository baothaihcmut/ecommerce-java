package com.ecommerceapp.products.adapter.transport.grpc.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import com.ecommerceapp.generated.products.CreateProductRequest;
import com.ecommerceapp.generated.products.CreateProductResponse;
import com.ecommerceapp.generated.products.ProductResponse;
import com.ecommerceapp.generated.products.ProductResponse.VariationResponse;
import com.ecommerceapp.libs.mappers.ProtoMapper;
import com.ecommerceapp.products.core.port.inbound.commands.CreateProductCommand;
import com.ecommerceapp.products.core.port.inbound.results.CreateProductResult;
import com.ecommerceapp.products.core.port.inbound.results.ProductResult;
import com.ecommerceapp.products.core.port.inbound.results.ProductResult.VariationResult;

@Mapper(componentModel = "spring", uses = { ProtoMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductGrpcMapper {

    @Mappings({
            @Mapping(source = "categoryIdsList", target = "categoryIds", qualifiedByName = "mapProtoStringListToString"),
            @Mapping(source = "variationsList", target = "variations", qualifiedByName = "mapProtoStringListToString"),
    })
    CreateProductCommand toCreateProductCommand(CreateProductRequest request);

    VariationResponse toVariationResponse(VariationResult result);

    @Mappings(value = {
            @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "mapInstantToTimestamp"),
            @Mapping(source = "updatedAt", target = "updatedAt", qualifiedByName = "mapInstantToTimestamp"),
            @Mapping(source = "categoryIds", target = "categoryIdsList", qualifiedByName = "mapListStringToProtoStringList"),
            @Mapping(source = "variations", target = "variationsList")
    })
    ProductResponse toProductResponse(ProductResult productResult);

    CreateProductResponse toCreateProductResponse(CreateProductResult result);

}
