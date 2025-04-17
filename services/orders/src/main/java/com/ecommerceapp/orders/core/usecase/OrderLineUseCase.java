package com.ecommerceapp.orders.core.usecase;

import org.springframework.stereotype.Service;

import com.ecommerceapp.libs.response.PaginationResponse;
import com.ecommerceapp.orders.core.port.inbound.handlers.OrderLineHandler;
import com.ecommerceapp.orders.core.port.inbound.queries.GetShopOrderLineQuery;
import com.ecommerceapp.orders.core.port.inbound.results.GetShopOrderLineResult;
import com.ecommerceapp.orders.core.port.inbound.results.OrderLineResult;
import com.ecommerceapp.orders.core.port.outbound.repositories.OrderLineRepository;
import com.ecommerceapp.orders.core.port.outbound.repositories.OrderLineRepository.OrderLineAndCountResult;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderLineUseCase implements OrderLineHandler {
    private final OrderLineRepository orderLineRepository;

    @Override
    public GetShopOrderLineResult getShopOrderLines(GetShopOrderLineQuery query) {
        OrderLineAndCountResult result = orderLineRepository.findOrderLineOfShopAndCount(query.shopId(), query.status(),
                query.pagination().getOffset(), query.pagination().getLimit());
        return new GetShopOrderLineResult(
                result.orderLines().stream()
                        .map(orderLine -> OrderLineResult.toOrderLineResult(orderLine,
                                orderLine.getOrder().getId().toString()))
                        .toList(),
                PaginationResponse.initPaginationResponse(query.pagination().getOffset(), query.pagination().getLimit(),
                        Long.valueOf(result.count()).intValue()));
    }

}
