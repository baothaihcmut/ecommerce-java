package com.ecommerceapp.products.core.usecase;

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
import com.ecommerceapp.products.core.domain.entities.Product;
import com.ecommerceapp.products.core.domain.entities.ProductItem;
import com.ecommerceapp.products.core.domain.entities.VariationValue;
import com.ecommerceapp.products.core.domain.projection.ProductItemWithProductProjection;
import com.ecommerceapp.products.core.exception.ErrorCode;
import com.ecommerceapp.products.core.port.inbound.commands.CreateProductItemCommand;
import com.ecommerceapp.products.core.port.inbound.handlers.ProductItemHandler;
import com.ecommerceapp.products.core.port.inbound.queries.GetProductItemWithProductQuery;
import com.ecommerceapp.products.core.port.inbound.results.CreateProductItemResult;
import com.ecommerceapp.products.core.port.inbound.results.GetProductItemWithProductByIdListResult;
import com.ecommerceapp.products.core.port.inbound.results.ProductItemResult;
import com.ecommerceapp.products.core.port.inbound.results.ProductItemWithProductResult;
import com.ecommerceapp.products.core.port.inbound.results.ProductResult;
import com.ecommerceapp.products.core.port.outbound.repositories.ProductItemRepository;
import com.ecommerceapp.products.core.port.outbound.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductItemUseCase implements ProductItemHandler {
        private final ProductRepository productRepository;
        private final ProductItemRepository productItemRepository;
        private final S3Service s3Service;

        @Override
        @Transactional
        public CreateProductItemResult createProductItem(CreateProductItemCommand command) {
                UserContext userContext = SecurityUtil.getUserContext();
                if (!userContext.isShopOwnerActive()) {
                        throw new AppException(ErrorCode.USER_NOT_SHOP_OWNER_ACTIVE);
                }
                // check product exist
                ObjectId productId = new ObjectId(command.productId());
                Product product = productRepository.findProductById(productId)
                                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXIST));
                // check if user is owner of shop

                // check variation
                List<VariationValue> values = command.variationValues().stream()
                                .map(input -> new VariationValue(
                                                new ObjectId(input.variationId()), input.value()))
                                .toList();
                product.checkVarions(values);

                ProductItem productItem = new ProductItem(
                                productId,
                                command.quantity(),
                                command.price(),
                                command.numOfImages(),
                                values);
                // save to db
                this.productItemRepository.save(productItem);
                // get presign url for upload image
                List<String> urls = productItem.getImages().stream()
                                .map(key -> s3Service.generatePresignUrlForPut(key, 30, MediaType.IMAGE_JPEG))
                                .toList();
                return new CreateProductItemResult(
                                ProductItemResult.fromProductItem(productItem),
                                urls.stream()
                                                .map(url -> new PresignUrlInfoResponse(url,
                                                                Instant.now().plus(30, ChronoUnit.MINUTES), "PUT",
                                                                MediaType.IMAGE_JPEG.toString()))
                                                .toList());

        }

        @Override
        public GetProductItemWithProductByIdListResult getProductItemWithProductByIdList(
                        GetProductItemWithProductQuery query) {
                List<ProductItemWithProductProjection> res = productItemRepository.findProductItemWithProductByIdList(
                                query.getIds().stream().map(id -> new ObjectId(id)).toList());
                List<ProductItemWithProductResult> result = res.stream()
                                .map(item -> ProductItemWithProductResult.builder()
                                                .product(item.getProduct() != null
                                                                ? ProductResult.toProductResult(item.getProduct())
                                                                : null)
                                                .productItem(ProductItemResult.fromProjection(item))
                                                .build())
                                .toList();
                return GetProductItemWithProductByIdListResult.builder().productItemWithProducts(result).build();
        }

}
