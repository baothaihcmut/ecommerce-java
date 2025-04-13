package com.ecommerceapp.shipment.core.usecase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.stereotype.Service;

import com.ecommerceapp.libs.exception.AppException;
import com.ecommerceapp.shipment.core.domain.entities.Address;
import com.ecommerceapp.shipment.core.domain.entities.ProductItem;
import com.ecommerceapp.shipment.core.exeption.ErrorCode;
import com.ecommerceapp.shipment.core.models.ShipProviderInfo;
import com.ecommerceapp.shipment.core.port.inbound.handlers.ShipProviderHandler;
import com.ecommerceapp.shipment.core.port.inbound.queries.GetShipmentFeeQuery;
import com.ecommerceapp.shipment.core.port.inbound.queries.GetShipmentFeeQuery.ShipItem;
import com.ecommerceapp.shipment.core.port.inbound.results.GetShipmentFeeResult;
import com.ecommerceapp.shipment.core.port.outbound.clients.ProductItemClient;
import com.ecommerceapp.shipment.core.port.outbound.clients.ShipProviderClient;
import com.ecommerceapp.shipment.core.port.outbound.clients.ShopClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShipProviderUseCase implements ShipProviderHandler {
    private final ShopClient shopClient;
    private final ProductItemClient productItemClient;
    private final List<ShipProviderClient> shipProviderClients;

    private record ProductItemWithQuantity(
            ProductItem productItem,
            Integer quantity) {
    }

    @Override
    public GetShipmentFeeResult getShipmentFee(GetShipmentFeeQuery query) {
        // find shop and product
        Map<String, Integer> mapQuantity = new HashMap<>();
        for (ShipItem shipItem : query.shipItems()) {
            mapQuantity.put(shipItem.productItemId(), shipItem.quantity());
        }
        Address shopAddress;
        List<ProductItemWithQuantity> productItemWithQuantities;
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<Address> shopAddressFuture = executorService.submit(() -> {
                return shopClient.findShopAddressById(query.shopId())
                        .orElseThrow(() -> new AppException(ErrorCode.SHOP_NOT_EXIST));
            });
            Future<List<ProductItem>> productItemsFuture = executorService.submit(() -> {
                return productItemClient
                        .findProductItemByIdList(query.shipItems().stream().map(ShipItem::productItemId).toList());
            });
            shopAddress = shopAddressFuture.get();
            productItemWithQuantities = productItemsFuture.get().stream()
                    .map(item -> new ProductItemWithQuantity(item, mapQuantity.get(item.getId()))).toList();
        } catch (ExecutionException exception) {
            if (exception.getCause() instanceof AppException appException) {
                throw appException;
            } else {
                throw new RuntimeException(exception.getMessage(), exception.getCause());
            }
        } catch (InterruptedException exception) {
            throw new RuntimeException(exception.getMessage(), exception.getCause());
        }

        Address receiveAddress = new Address(
                query.recieveAddress(),
                query.recieveStreet(),
                query.recieveWard(),
                query.recieveDistrict(),
                query.recieveProvince());

        // get shipment fee
        List<Future<Integer>> futureFees = new ArrayList<>();
        // get client
        ShipProviderClient shipProviderClient = shipProviderClients
                .stream()
                .filter(client -> client.getShipProvider().equals(query.shipProvider()))
                .findFirst().orElseThrow(() -> new AppException(ErrorCode.SHIP_PROVIDER_NOT_SUPPORT));
        // get all fee of each item (feeEachItem = fee*quantity)
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            for (ProductItemWithQuantity productItemWithQuantity : productItemWithQuantities) {
                Future<Integer> futureFee = executorService.submit(() -> {
                    return shipProviderClient.getShipmentFee(
                            ShipProviderInfo.toShipProviderInfo(productItemWithQuantity.productItem().getWeight(),
                                    receiveAddress, shopAddress))
                            .fee() * productItemWithQuantity.quantity();
                });
                futureFees.add(futureFee);
            }
            int totalFee = 0;
            for (Future<Integer> futureFee : futureFees) {
                totalFee += futureFee.get();
            }
            return new GetShipmentFeeResult(totalFee);
        } catch (ExecutionException exception) {
            if (exception.getCause() instanceof AppException appException) {
                throw appException;
            } else {
                throw new RuntimeException(exception.getMessage(), exception.getCause());
            }
        } catch (InterruptedException exception) {
            throw new RuntimeException(exception.getMessage(), exception.getCause());
        }

    }

}
