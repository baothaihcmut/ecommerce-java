package com.ecommerceapp.shops.core.usecase;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerceapp.libs.exception.AppException;
import com.ecommerceapp.libs.response.PaginationResponse;
import com.ecommerceapp.libs.response.PresignUrlInfoResponse;
import com.ecommerceapp.libs.s3.S3Service;
import com.ecommerceapp.libs.saga.SagaAction;
import com.ecommerceapp.libs.security.SecurityUtil;
import com.ecommerceapp.libs.security.SecurityUtil.UserContext;
import com.ecommerceapp.shops.core.domain.entities.Product;
import com.ecommerceapp.shops.core.domain.entities.Shop;
import com.ecommerceapp.shops.core.exception.ErrorCode;
import com.ecommerceapp.shops.core.patterns.saga.products.CreateProductSagaAction;
import com.ecommerceapp.shops.core.patterns.saga.shops.IncreShopProductAmountSagaAction;
import com.ecommerceapp.shops.core.port.inbound.commands.CreateProductCommand;
import com.ecommerceapp.shops.core.port.inbound.handlers.ShopProductHandler;
import com.ecommerceapp.shops.core.port.inbound.queries.GetProductsOfShopQuery;
import com.ecommerceapp.shops.core.port.inbound.results.CreateProductResult;
import com.ecommerceapp.shops.core.port.inbound.results.GetProductsOfShopResult;
import com.ecommerceapp.shops.core.port.inbound.results.ProductResult;
import com.ecommerceapp.shops.core.port.inbound.results.ProductWithThumbnailResult;
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
        List<SagaAction<?>> actions = new ArrayList<>();
        try {
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
            CreateProductSagaAction createProductSagaAction = new CreateProductSagaAction(command,
                    productClient);
            actions.add(createProductSagaAction);
            IncreShopProductAmountSagaAction increShopProductAmountSagaAction = new IncreShopProductAmountSagaAction(
                    shopRepository, shop, 1);
            actions.add(increShopProductAmountSagaAction);

            Product product = createProductSagaAction.execute();
            increShopProductAmountSagaAction.execute();

            String thumbnailUrl = this.s3Service.generatePresignUrlForPut(product.getThumbnail(), 30,
                    MediaType.IMAGE_PNG);
            List<String> imageUrls = product.getImages().stream()
                    .map(key -> this.s3Service.generatePresignUrlForPut(key, 30,
                            MediaType.IMAGE_PNG))
                    .toList();
            return CreateProductResult.builder()
                    .product(ProductResult.toProductResult(product))
                    .imagePresignUrls(imageUrls.stream()
                            .map(url -> new PresignUrlInfoResponse(url,
                                    Instant.now().plus(30,
                                            ChronoUnit.MINUTES),
                                    "PUT", MediaType.IMAGE_JPEG.toString()))
                            .toList())
                    .thumbnailPresignUrl(new PresignUrlInfoResponse(thumbnailUrl,
                            Instant.now().plus(30, ChronoUnit.MINUTES), "PUT",
                            MediaType.IMAGE_PNG.toString()))
                    .build();
        } catch (Exception e) {
            for (SagaAction<?> action : actions) {
                action.abort();
            }
            throw e;
        }
    }

    @Override
    public GetProductsOfShopResult getProductsOfShop(GetProductsOfShopQuery query) {
        ProductClient.GetProductOfShopResult res = productClient
                .getProductsOfShop(query.getShopId(), query.getLimit(), query.getOffset());

        List<ProductWithThumbnailResult> results = res.products()
                .stream()
                .map(product -> ProductWithThumbnailResult.builder()
                        .product(ProductResult.toProductResult(product))
                        .thumbnailPresignUrlInfo(query.getHasThumbnail()
                                ? new PresignUrlInfoResponse(
                                        s3Service.generatePresignUrlForGet(
                                                product.getId(),
                                                30,
                                                MediaType.IMAGE_JPEG),
                                        Instant.now().plus(30,
                                                ChronoUnit.MINUTES),
                                        "GET", MediaType.IMAGE_JPEG_VALUE)

                                : null)
                        .build())
                .toList();
        return GetProductsOfShopResult.builder()
                .products(results)
                .pagination(PaginationResponse.initPaginationResponse(query.getOffset(),
                        query.getLimit(), res.count()))
                .build();
    }
}
