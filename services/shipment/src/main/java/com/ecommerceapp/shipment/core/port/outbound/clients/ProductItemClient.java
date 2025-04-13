package com.ecommerceapp.shipment.core.port.outbound.clients;

import java.util.List;

import com.ecommerceapp.shipment.core.domain.entities.ProductItem;

public interface ProductItemClient {
    List<ProductItem> findProductItemByIdList(List<String> ids);
}
