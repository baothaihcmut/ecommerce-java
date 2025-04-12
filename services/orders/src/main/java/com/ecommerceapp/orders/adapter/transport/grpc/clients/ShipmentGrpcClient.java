package com.ecommerceapp.orders.adapter.transport.grpc.clients;

import org.springframework.stereotype.Service;

import com.ecommerceapp.orders.core.port.outbound.clients.ShipmentClient;

@Service
public class ShipmentGrpcClient implements ShipmentClient {

    @Override
    public Integer getShippingFee(GetShippingFeeArg arg) {
        // call shipment via grpc
        return 10000;
    }

}
