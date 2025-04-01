package com.ecommerceapp.shops.core.usecase;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerceapp.libs.exception.AppException;
import com.ecommerceapp.libs.response.PresignUrlInfoResponse;
import com.ecommerceapp.libs.s3.S3Service;
import com.ecommerceapp.libs.security.SecurityUtil;
import com.ecommerceapp.libs.security.SecurityUtil.UserContext;
import com.ecommerceapp.shops.core.domain.entities.Product;
import com.ecommerceapp.shops.core.domain.entities.Shop;
import com.ecommerceapp.shops.core.exception.ErrorCode;
import com.ecommerceapp.shops.core.port.inbound.commands.CreateProductCommand;
import com.ecommerceapp.shops.core.port.inbound.handlers.ShopProductHandler;
import com.ecommerceapp.shops.core.port.inbound.results.CreateProductResult;
import com.ecommerceapp.shops.core.port.inbound.results.ProductResult;
import com.ecommerceapp.shops.core.port.outbound.clients.ProductClient;
import com.ecommerceapp.shops.core.port.outbound.repositories.ShopRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShopProductUseCase implements ShopProductHandler {
    private final ShopRepository shopRepository;
    private final ProductClient productClient;
    private final S3Service s3Service;

    @Override
    @Transactional
    public CreateProductResult createProduct(CreateProductCommand command) {
        UserContext userContext = SecurityUtil.getUserContext();
        if (!userContext.isShopOwnerActive()) {
            throw new AppException(ErrorCode.USER_NOT_SHOP_OWNER_ACTIVE);
        }
        ObjectId shopId = new ObjectId(command.getShopId());
        Shop shop = shopRepository.findShopById(shopId)
                .orElseThrow(() -> new AppException(ErrorCode.SHOP_NOT_EXIST));
        if (!userContext.userId().equals(shop.getOwnerId())) {
            throw new AppException(ErrorCode.SHOP_NOT_BELONG_TO_USER);
        }
        // createProduct

        shop.addProducts(1);
        Product product = productClient.createProduct(command);
        shopRepository.save(shop);
        // get presign url for image
        String thumbnailUrl = this.s3Service.generatePresignUrlForPut(product.getThumbnail(), 30,
                MediaType.IMAGE_PNG);
        List<String> imageUrls = product.getImages().stream()
                .map(key -> this.s3Service.generatePresignUrlForPut(key, 30, MediaType.IMAGE_PNG))
                .toList();
        return CreateProductResult.builder()
                .product(ProductResult.toProductResult(product))
                .imagePresignUrls(imageUrls.stream()
                        .map(url -> PresignUrlInfoResponse.builder()
                                .url(url)
                                .expireAt(Instant.now().plus(30, ChronoUnit.MINUTES))
                                .method("PUT")
                                .contentType(MediaType.IMAGE_JPEG.toString())
                                .build())
                        .toList())
                .thumbnailPresignUrl(PresignUrlInfoResponse.builder()
                        .url(thumbnailUrl)
                        .expireAt(Instant.now().plus(30, ChronoUnit.MINUTES))
                        .contentType(MediaType.IMAGE_PNG.toString())
                        .method("PUT")
                        .build())
                .build();
    }
}
