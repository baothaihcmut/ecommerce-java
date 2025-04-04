package com.ecommerceapp.products.adapter.transport.grpc.mappers;

import org.mapstruct.AfterMapping;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import com.ecommerceapp.generated.products.CreateProductRequest;
import com.ecommerceapp.generated.products.CreateProductResponse;
import com.ecommerceapp.generated.products.DeleteProductRequest;
import com.ecommerceapp.generated.products.DeleteProductResponse;
import com.ecommerceapp.generated.products.GetProductsOfShopRequest;
import com.ecommerceapp.generated.products.GetProductsOfShopResponse;
import com.ecommerceapp.generated.products.ProductResponse;
import com.ecommerceapp.generated.products.ProductResponse.VariationResponse;
import com.ecommerceapp.libs.grpc.mappers.ProtoMapper;
import com.ecommerceapp.products.core.port.inbound.commands.CreateProductCommand;
import com.ecommerceapp.products.core.port.inbound.commands.DeleteProductCommand;
import com.ecommerceapp.products.core.port.inbound.queries.GetProductsOfShopQuery;
import com.ecommerceapp.products.core.port.inbound.results.CreateProductResult;
import com.ecommerceapp.products.core.port.inbound.results.DeleteProductResult;
import com.ecommerceapp.products.core.port.inbound.results.GetProductsOfShopResult;
import com.ecommerceapp.products.core.port.inbound.results.ProductResult;
import com.ecommerceapp.products.core.port.inbound.results.ProductResult.VariationResult;

@Mapper(componentModel = "spring", uses = {
                ProtoMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE, collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface ProductGrpcMapper {

        @Mappings({
                        @Mapping(source = "categoryIdsList", target = "categoryIds", qualifiedByName = "mapProtoStringListToString"),
                        @Mapping(source = "variationsList", target = "variations", qualifiedByName = "mapProtoStringListToString"),
        })
        CreateProductCommand toCreateProductCommand(CreateProductRequest request);

        CreateProductResponse toCreateProductResponse(CreateProductResult result);

        DeleteProductCommand toDeleteProductCommand(DeleteProductRequest request);

        DeleteProductResponse toDeleteProductResponse(DeleteProductResult result);

        GetProductsOfShopQuery toGetProductsOfShopQuery(GetProductsOfShopRequest request);

        @Mappings(value = {
                        @Mapping(source = "products", target = "productsList")
        })
        GetProductsOfShopResponse toGetProductsOfShopResponse(GetProductsOfShopResult res);

        VariationResponse map(VariationResult result);

        @Mappings(value = {
                        @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "mapInstantToTimestamp"),
                        @Mapping(source = "updatedAt", target = "updatedAt", qualifiedByName = "mapInstantToTimestamp"),
                        @Mapping(source = "variations", target = "variationsList")
        })
        ProductResponse.Builder map(ProductResult productResult);

        default ProductResponse.Builder productResponseBuilder() {
                return ProductResponse.newBuilder();
        }

        default ProductResponse buid(ProductResponse.Builder builder) {
                return builder.build();
        }

        @AfterMapping
        default void addMapCategoryIdList(@MappingTarget ProductResponse.Builder builder, ProductResult res) {
                builder.addAllCategoryIds(res.getCategoryIds()).addAllImages(res.getImages());
        }

}
