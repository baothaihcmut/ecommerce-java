package com.ecommerceapp.shops.adapter.transport.grpc.mappers;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import com.ecommerceapp.generated.products.CreateProductRequest;
import com.ecommerceapp.generated.products.ProductResponse;
import com.ecommerceapp.generated.products.ProductResponse.VariationResponse;
import com.ecommerceapp.libs.grpc.mappers.ProtoMapper;
import com.ecommerceapp.shops.core.domain.entities.Product;
import com.ecommerceapp.shops.core.domain.entities.Product.Variation;
import com.ecommerceapp.shops.core.port.inbound.commands.CreateProductCommand;

@Mapper(componentModel = "spring", uses = { ProtoMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductGrpcMapper {

        CreateProductRequest.Builder toCreateProductRequest(CreateProductCommand product);

        Variation toVariation(VariationResponse response);

        @Mappings(value = {
                        @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "mapTimestampToInstant"),
                        @Mapping(source = "updatedAt", target = "updatedAt", qualifiedByName = "mapTimestampToInstant"),
                        @Mapping(source = "shopId", target = "shopId", qualifiedByName = "mapStringToObjectId"),
                        @Mapping(source = "categoryIdsList", target = "categoryIds", qualifiedByName = "mapProtoStringListToString"),
                        @Mapping(source = "variationsList", target = "variations", qualifiedByName = "mapVariationResponseList"),
                        @Mapping(source = "imagesList", target = "images", qualifiedByName = "mapProtoStringListToString")

        })
        Product toProduct(ProductResponse productResponse);

        @Named("mapVariationResponseList")
        default List<Variation> mapVariationResponseList(List<ProductResponse.VariationResponse> list) {
                return list.stream().map(this::toVariation).toList();
        }

        default CreateProductRequest.Builder map() {
                return CreateProductRequest.newBuilder();
        }

        default CreateProductRequest map(CreateProductRequest.Builder builder) {
                return builder.build();
        }

        @AfterMapping
        default void addMapStringList(@MappingTarget CreateProductRequest.Builder builder,
                        CreateProductCommand command) {
                builder.addAllCategoryIds(command.getCategoryIds())
                                .addAllVariations(command.getVariations())
                                .build();
        }

}
