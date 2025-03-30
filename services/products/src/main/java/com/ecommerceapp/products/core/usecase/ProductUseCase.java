package com.ecommerceapp.products.core.usecase;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerceapp.libs.events.products.ProductAdditionEvent;
import com.ecommerceapp.libs.exception.AppException;
import com.ecommerceapp.libs.interceptors.UserContextInterceptor;
import com.ecommerceapp.libs.models.UserContext;
import com.ecommerceapp.libs.s3.S3Service;
import com.ecommerceapp.products.core.domain.entities.Category;
import com.ecommerceapp.products.core.domain.entities.Product;
import com.ecommerceapp.products.core.domain.entities.Shop;
import com.ecommerceapp.products.core.exception.ErrorCode;
import com.ecommerceapp.products.core.port.inbound.commands.CreateProductCommand;
import com.ecommerceapp.products.core.port.inbound.handlers.ProductHandler;
import com.ecommerceapp.products.core.port.inbound.results.CreateProductResult;
import com.ecommerceapp.products.core.port.inbound.results.ProductResult;
import com.ecommerceapp.products.core.port.inbound.results.UploadImageResult;
import com.ecommerceapp.products.core.port.outbound.clients.ShopClient;
import com.ecommerceapp.products.core.port.outbound.events.ProductEventPublisher;
import com.ecommerceapp.products.core.port.outbound.repositories.CategoryRepository;
import com.ecommerceapp.products.core.port.outbound.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductUseCase implements ProductHandler {
        private final ShopClient shopService;
        private final CategoryRepository categoryRepository;
        private final ProductRepository productRepository;
        private final S3Service s3Service;
        private final ProductEventPublisher productEventPublisher;

        @Override
        @Transactional
        public CreateProductResult createProduct(CreateProductCommand command) {
                UserContext userContext = UserContextInterceptor.getUserContext();
                if (!userContext.getIsShopOwnerActive()) {
                        throw new AppException(ErrorCode.USER_NOT_SHOP_OWNER_ACTIVE);
                }
                Shop shop = this.shopService.findShopById(command.getShopId())
                                .orElseThrow(() -> new AppException(ErrorCode.SHOP_NOT_EXIST));
                if (!userContext.getUserId().equals(shop.getShopOwnerId())) {
                        throw new AppException(ErrorCode.USER_NOT_SHOP_OWNER);
                }
                // find all category
                if (command.getCategoryIds().size() > 0) {
                        List<Category> categories = this.categoryRepository
                                        .findCategoryByIdList(command.getCategoryIds());
                        Set<ObjectId> categoryIds = new HashSet<>(command.getCategoryIds());
                        for (Category category : categories) {
                                if (categoryIds.contains(category.getId())) {
                                        categoryIds.remove(category.getId());
                                }
                        }
                        if (categoryIds.size() > 0) {
                                throw new AppException(
                                                ErrorCode.CATEGORY_NOT_EXIST
                                                                .withDetails(Map.of("categoryId", categoryIds.iterator()
                                                                                .next().toString())));
                        }
                }
                // init new product
                Product product = new Product(
                                command.getName(),
                                command.getDescription(),
                                command.getHasThumbnail(),
                                command.getNumOfImage(),
                                command.getCategoryIds(),
                                command.getShopId(),
                                command.getVariations());
                this.productRepository.save(product);
                // publish event
                productEventPublisher.publishAddProductEvent(toProductAdditionEvent(product));
                // generate url for upload image
                String thumbnailUrl = this.s3Service.generatePresignUrlForPut(product.getThumbnail(), 30,
                                MediaType.IMAGE_PNG);
                List<String> imageUrls = product.getImages().stream()
                                .map(key -> this.s3Service.generatePresignUrlForPut(key, 30, MediaType.IMAGE_PNG))
                                .toList();
                return CreateProductResult.builder()
                                .product(ProductResult.toProductResult(product))
                                .uploadImageInfo(imageUrls.stream()
                                                .map(url -> UploadImageResult.builder()
                                                                .url(url)
                                                                .expireAt(Instant.now().plus(30, ChronoUnit.MINUTES))
                                                                .method("PUT")
                                                                .contentType(MediaType.IMAGE_JPEG.toString())
                                                                .build())
                                                .toList())
                                .uploadThumbnailInfo(UploadImageResult.builder()
                                                .url(thumbnailUrl)
                                                .expireAt(Instant.now().plus(30, ChronoUnit.MINUTES))
                                                .contentType(MediaType.IMAGE_PNG.toString())
                                                .method("PUT")
                                                .build())
                                .build();

        }

        private ProductAdditionEvent toProductAdditionEvent(Product product) {
                return ProductAdditionEvent.builder()
                                .id(product.getId().toHexString())
                                .name(product.getName())
                                .shopId(product.getShopId())
                                .description(product.getDescription())
                                .categoryIds(product.getCategoryIds().stream().map(id -> id.toHexString()).toList())
                                .variations(product.getVariations().stream()
                                                .map(variation -> new ProductAdditionEvent.Variation(
                                                                variation.getId().toHexString(), variation.getName()))
                                                .toList())
                                .images(product.getImages())
                                .thumbnail(product.getThumbnail())
                                .createdAt(product.getCreatedAt())
                                .updatedAt(product.getUpdatedAt())
                                .build();
        }

}
