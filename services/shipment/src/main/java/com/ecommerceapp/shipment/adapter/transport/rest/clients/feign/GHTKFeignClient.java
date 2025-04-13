package com.ecommerceapp.shipment.adapter.transport.rest.clients.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.ecommerceapp.shipment.adapter.transport.rest.clients.dtos.GHTKGetShipmentFeeRequestDTO;
import com.ecommerceapp.shipment.adapter.transport.rest.clients.dtos.GHTKGetShipmentFeeResponseDTO;

@FeignClient(name = "ghtk-client", url = "${ship-provider.ghtk.url}")
public interface GHTKFeignClient {

    @GetMapping("/services/shipment/fee")
    GHTKGetShipmentFeeResponseDTO getShipmentFee(@SpringQueryMap GHTKGetShipmentFeeRequestDTO dto);

}
