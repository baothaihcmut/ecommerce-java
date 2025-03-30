package com.ecommerceapp.products.adapter.transport.rest.clients;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.ecommerceapp.libs.dtos.shop.response.GetShopByIdResponseDTO;
import com.ecommerceapp.products.config.ServiceAddressProperties;
import com.ecommerceapp.products.core.domain.entities.Shop;
import com.ecommerceapp.products.core.port.outbound.clients.ShopClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestShopClient implements ShopClient {
    private final RestTemplate restTemplate;
    private final ServiceAddressProperties serviceAddressProperties;

    @Override
    public Optional<Shop> findShopById(String shopId) {
        try {
            String url = String.format(
                    "http://%s/internal/shops/%s",
                    serviceAddressProperties.getShopService(),
                    shopId);
            ResponseEntity<GetShopByIdResponseDTO> response = restTemplate.getForEntity(url,
                    GetShopByIdResponseDTO.class);
            Shop shop = new Shop(
                    response.getBody().getShop().getId().toHexString(),
                    response.getBody().getShop().getOwnerId());
            return Optional.of(shop);

        } catch (HttpClientErrorException httpException) {
            if (httpException.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                return Optional.empty();
            }
            throw httpException;
        }

    }

}
