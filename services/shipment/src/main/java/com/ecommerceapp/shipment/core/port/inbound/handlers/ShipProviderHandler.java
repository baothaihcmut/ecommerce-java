package com.ecommerceapp.shipment.core.port.inbound.handlers;

import com.ecommerceapp.shipment.core.port.inbound.queries.GetShipmentFeeQuery;
import com.ecommerceapp.shipment.core.port.inbound.results.GetShipmentFeeResult;

public interface ShipProviderHandler {
    GetShipmentFeeResult getShipmentFee(GetShipmentFeeQuery query);
}
