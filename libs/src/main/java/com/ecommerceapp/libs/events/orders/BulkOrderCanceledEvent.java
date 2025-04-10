package com.ecommerceapp.libs.events.orders;

import java.util.List;

public record BulkOrderCanceledEvent(List<OrderEvent> orders) {

}
