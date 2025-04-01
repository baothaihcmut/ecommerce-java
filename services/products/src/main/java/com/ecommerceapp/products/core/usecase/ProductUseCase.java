package com.ecommerceapp.products.core.usecase;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerceapp.libs.exception.AppException;
import com.ecommerceapp.products.core.domain.entities.Category;
import com.ecommerceapp.products.core.domain.entities.Product;
import com.ecommerceapp.products.core.exception.ErrorCode;
import com.ecommerceapp.products.core.port.inbound.commands.CreateProductCommand;
import com.ecommerceapp.products.core.port.inbound.handlers.ProductHandler;
import com.ecommerceapp.products.core.port.inbound.results.CreateProductResult;
import com.ecommerceapp.products.core.port.inbound.results.ProductResult;
import com.ecommerceapp.products.core.port.outbound.repositories.CategoryRepository;
import com.ecommerceapp.products.core.port.outbound.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductUseCase implements ProductHandler {
        private final CategoryRepository categoryRepository;
        private final ProductRepository productRepository;

        @Override
        @Transactional
        public CreateProductResult createProduct(CreateProductCommand command) {
                List<ObjectId> categoryIds = command.getCategoryIds().stream().map(id -> new ObjectId(id))
                                .toList();
                // find all category
                if (command.getCategoryIds().size() > 0) {

                        List<Category> categories = this.categoryRepository
                                        .findCategoryByIdList(categoryIds);
                        Set<ObjectId> categorySet = new HashSet<>(categoryIds);
                        for (Category category : categories) {
                                if (categoryIds.contains(category.getId())) {
                                        categorySet.remove(category.getId());
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
