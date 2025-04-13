package com.ecommerceapp.shipment.core.port.outbound.clients;

import com.ecommerceapp.shipment.core.domain.enums.ShipProvider;
import com.ecommerceapp.shipment.core.models.ShipProviderInfo;

public interface ShipProviderClient {
    public record GetShipmentFeeResult(
            Integer fee,
            String name) {
    }

    GetShipmentFeeResult getShipmentFee(ShipProviderInfo shipProviderInfo);

    ShipProvider getShipProvider();
}
