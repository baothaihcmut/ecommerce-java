package com.ecommerceapp.products.core.usecase;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerceapp.libs.exception.AppException;
import com.ecommerceapp.libs.security.SecurityUtil;
import com.ecommerceapp.libs.security.SecurityUtil.UserContext;
import com.ecommerceapp.products.core.domain.entities.Category;
import com.ecommerceapp.products.core.domain.entities.Product;
import com.ecommerceapp.products.core.domain.entities.Shop;
import com.ecommerceapp.products.core.exception.ErrorCode;
import com.ecommerceapp.products.core.port.inbound.commands.CreateProductCommand;
import com.ecommerceapp.products.core.port.inbound.handlers.ProductHandler;
import com.ecommerceapp.products.core.port.inbound.results.CreateProductResult;
import com.ecommerceapp.products.core.port.inbound.results.ProductResult;
import com.ecommerceapp.products.core.port.outbound.clients.ShopClient;
import com.ecommerceapp.products.core.port.outbound.repositories.CategoryRepository;
import com.ecommerceapp.products.core.port.outbound.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductUseCase implements ProductHandler {
        private final ShopClient shopService;
        private final CategoryRepository categoryRepository;
        private final ProductRepository productRepository;

        @Override
        @Transactional
        public CreateProductResult createProduct(CreateProductCommand command) {
                UserContext userContext = SecurityUtil.getUserContext();
                if (!userContext.isShopOwnerActive()) {
                        throw new AppException(ErrorCode.USER_NOT_SHOP_OWNER_ACTIVE);
                }
                List<ObjectId> categoryIds = command.getCategoryIds().stream().map(id -> new ObjectId(id))
                                .toList();
                Shop shop = this.shopService.findShopById(command.getShopId())
                                .orElseThrow(() -> new AppException(ErrorCode.SHOP_NOT_EXIST));
                if (!userContext.userId().equals(shop.getShopOwnerId())) {
                        throw new AppException(ErrorCode.USER_NOT_SHOP_OWNER);
                }
                // find all category
                if (command.getCategoryIds().size() > 0) {

                        List<Category> categories = this.categoryRepository
                                        .findCategoryByIdList(categoryIds);
                        Set<ObjectId> categorySet = new HashSet<>(categoryIds);
                        for (Category category : categories) {
                                if (categoryIds.contains(category.getId())) {
                                        categoryIds.remove(category.getId());
                                }
                        }
                        if (categorySet.size() > 0) {
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
                                categoryIds,
                                command.getShopId(),
                                command.getVariations());
                this.productRepository.save(product);

                return CreateProductResult.builder()
                                .product(ProductResult.toProductResult(product))
                                .build();

        }

}
