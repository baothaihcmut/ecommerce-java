package com.ecommerceapp.shipment.adapter.transport.rest.clients.providers;

import org.springframework.stereotype.Service;

import com.ecommerceapp.shipment.adapter.transport.rest.clients.dtos.GHTKGetShipmentFeeRequestDTO;
import com.ecommerceapp.shipment.adapter.transport.rest.clients.dtos.GHTKGetShipmentFeeResponseDTO;
import com.ecommerceapp.shipment.adapter.transport.rest.clients.feign.GHTKFeignClient;
import com.ecommerceapp.shipment.core.domain.enums.ShipProvider;
import com.ecommerceapp.shipment.core.models.ShipProviderInfo;
import com.ecommerceapp.shipment.core.port.outbound.clients.ShipProviderClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GHTKShipProviderClient implements ShipProviderClient {
    private GHTKFeignClient ghtkFeignClient;

    @Override
    public GetShipmentFeeResult getShipmentFee(ShipProviderInfo shipProviderInfo) {
        GHTKGetShipmentFeeResponseDTO responseDTO = ghtkFeignClient.getShipmentFee(
                GHTKGetShipmentFeeRequestDTO.fromShipProviderInfo(shipProviderInfo));
        return new GetShipmentFeeResult(
                responseDTO.fee().fee(), responseDTO.fee().name());
    }

    @Override
    public ShipProvider getShipProvider() {
        return ShipProvider.GHTK;
    }

}
