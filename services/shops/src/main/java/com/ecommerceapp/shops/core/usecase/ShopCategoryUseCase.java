package com.ecommerceapp.shops.core.usecase;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerceapp.libs.exception.AppException;
import com.ecommerceapp.libs.security.SecurityUtil;
import com.ecommerceapp.libs.security.SecurityUtil.UserContext;
import com.ecommerceapp.shops.core.domain.entities.Shop;
import com.ecommerceapp.shops.core.domain.entities.ShopCategory;
import com.ecommerceapp.shops.core.exception.ErrorCode;
import com.ecommerceapp.shops.core.port.inbound.commands.CreateShopCategoryCommand;
import com.ecommerceapp.shops.core.port.inbound.handlers.ShopCategoryHandler;
import com.ecommerceapp.shops.core.port.inbound.queries.GetShopCategoriesOfShopQuery;
import com.ecommerceapp.shops.core.port.inbound.results.CreateShopCategoryResult;
import com.ecommerceapp.shops.core.port.inbound.results.GetShopCategoriesOfShopResult;
import com.ecommerceapp.shops.core.port.inbound.results.ShopCategoryResult;
import com.ecommerceapp.shops.core.port.outbound.repositories.ShopCategoryRepository;
import com.ecommerceapp.shops.core.port.outbound.repositories.ShopRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShopCategoryUseCase implements ShopCategoryHandler {
    private final ShopCategoryRepository shopCategoryRepository;
    private final ShopRepository shopRepository;

    @Override
    @Transactional
    public CreateShopCategoryResult createShopCategory(CreateShopCategoryCommand categoryCommand) {
        UserContext userContext = SecurityUtil.getUserContext();
        if (!userContext.isShopOwnerActive()) {
            throw new AppException(ErrorCode.USER_NOT_SHOP_OWNER_ACTIVE);
        }
        ObjectId shopId = new ObjectId(categoryCommand.getShopId());
        Shop shop = shopRepository.findShopById(shopId).orElseThrow(() -> new AppException(ErrorCode.SHOP_NOT_EXIST));
        if (!userContext.userId().equals(shop.getOwnerId())) {
            throw new AppException(ErrorCode.SHOP_NOT_BELONG_TO_USER);
        }
        ObjectId parentCategoryId = null;
        if (categoryCommand.getParentShopCategoryId() != null) {
            parentCategoryId = new ObjectId(categoryCommand.getParentShopCategoryId());
            shopCategoryRepository.findShopCategoryById(parentCategoryId)
                    .orElseThrow(() -> new AppException(ErrorCode.SHOP_CATEGORY_NOT_EXIST));
        }
        ShopCategory shopCategory = new ShopCategory(
                categoryCommand.getName(),
                parentCategoryId,
                shopId);
        this.shopCategoryRepository.save(shopCategory);
        return CreateShopCategoryResult.builder()
                .shopCategory(ShopCategoryResult.toShopCategoryResult(shopCategory))
                .build();
    }

    @Override
    public GetShopCategoriesOfShopResult getCategoriesOfShop(GetShopCategoriesOfShopQuery query) {
        ObjectId shopId = new ObjectId(query.getShopId());
        List<ShopCategory> categories = shopCategoryRepository.findShopCategoryByShopId(shopId);
        return GetShopCategoriesOfShopResult.builder()
                .shopCategories(
                        categories.stream().map(category -> ShopCategoryResult.toShopCategoryResult(category))
                                .toList())
                .build();
    }

}
