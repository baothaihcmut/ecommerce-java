package com.ecommerceapp.orders.core.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ecommerceapp.libs.security.SecurityUtil;
import com.ecommerceapp.libs.security.SecurityUtil.UserContext;
import com.ecommerceapp.orders.core.domain.entities.Address;
import com.ecommerceapp.orders.core.domain.entities.Order;
import com.ecommerceapp.orders.core.domain.entities.OrderLine;
import com.ecommerceapp.orders.core.domain.entities.ProductItem;
import com.ecommerceapp.orders.core.domain.enums.OrderStatus;
import com.ecommerceapp.orders.core.domain.enums.ShipProvider;
import com.ecommerceapp.orders.core.domain.events.OrderCreatedEvent;
import com.ecommerceapp.orders.core.port.inbound.commands.CreateOrderCommand;
import com.ecommerceapp.orders.core.port.inbound.commands.UpdateOrderAddressCommand;
import com.ecommerceapp.orders.core.port.inbound.commands.CreateOrderCommand.OrderItem;
import com.ecommerceapp.orders.core.port.inbound.results.CreateOrderResult;
import com.ecommerceapp.orders.core.port.inbound.results.OrderLineResult;
import com.ecommerceapp.orders.core.port.inbound.results.OrderResult;
import com.ecommerceapp.orders.core.port.inbound.results.UpdateOrderAddressResult;
import com.ecommerceapp.orders.core.port.outbound.clients.ProductItemClient;
import com.ecommerceapp.orders.core.port.outbound.clients.ShipmentClient;
import com.ecommerceapp.orders.core.port.outbound.clients.UserAddressClient;
import com.ecommerceapp.orders.core.port.outbound.publishers.OrderEventPublisher;
import com.ecommerceapp.orders.core.port.outbound.repositories.OrderRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OrderUseCaseTest {
    @Mock
    private ProductItemClient productItemClient;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderEventPublisher orderEventPublisher;

    @Mock
    private ShipmentClient shipmentClient;

    @Mock
    private UserAddressClient userAddressClient;

    @InjectMocks
    private OrderUseCase orderUseCase;

    private MockedStatic<SecurityUtil> securityUtilMock;

    @BeforeEach
    void setup() {
        securityUtilMock = Mockito.mockStatic(SecurityUtil.class);
        securityUtilMock.when(SecurityUtil::getUserContext)
                .thenReturn(new UserContext("123", true));
    }

    @AfterEach
    void clean() {
        if (securityUtilMock != null) {
            securityUtilMock.close();
        }
    }

    @Test
    public void createOrder_success() throws Exception {
        OrderItem orderItem1 = new OrderItem(UUID.randomUUID().toString(), 1);
        OrderItem orderItem2 = new OrderItem(UUID.randomUUID().toString(), 2);
        List<OrderItem> orderItems = List.of(orderItem1, orderItem2);

        String shopId = UUID.randomUUID().toString();
        CreateOrderCommand command = new CreateOrderCommand(orderItems);
        ProductItem productItem1 = new ProductItem(
                orderItem1.productItemId(), UUID.randomUUID().toString(),
                10, 10000, shopId);
        ProductItem productItem2 = new ProductItem(
                orderItem2.productItemId(), UUID.randomUUID().toString(),
                10, 10000, shopId);
        List<ProductItem> productItems = List.of(productItem1, productItem2);
        Mockito.when(
                productItemClient.findProductItemByIdList(
                        orderItems.stream().map(OrderItem::productItemId).toList()))
                .thenReturn(productItems);
        doNothing().when(orderRepository).save(Mockito.any(Order.class));
        doNothing().when(orderEventPublisher).publishOrderCreatedEvent(Mockito.any(OrderCreatedEvent.class));
        CreateOrderResult createOrderResult = orderUseCase.createOrder(command);
        // check not null

        assertNotNull(createOrderResult);
        assertNotNull(createOrderResult.order().id());
        assertEquals(
                createOrderResult.order().totalAmount(),
                orderItem1.quantity() * productItem1.getPrice()
                        + orderItem2.quantity() * productItem2.getPrice());
        assertEquals(createOrderResult.order().userId(), "123");
        assertEquals(createOrderResult.order().status(), OrderStatus.PENDING);
        assertNull(createOrderResult.order().paymentMethod());
        assertNull(createOrderResult.order().paymentProvider());
        assertNull(createOrderResult.order().recieveAddress());
        assertNotNull(createOrderResult.order().createdAt());
        assertNull(createOrderResult.order().confirmedAt());
        assertNull(createOrderResult.order().paidAt());
        assertNull(createOrderResult.order().shippedAt());
        assertNull(createOrderResult.order().recievedAt());
        assertNull(createOrderResult.order().shipProvider());
        assertEquals(createOrderResult.order().shippingCost(), 0);
        assertEquals(createOrderResult.order().orderLines().size(), 2);
        // order line 1
        OrderLineResult orderLineResult1 = createOrderResult.order().orderLines().get(0);
        assertNotNull(orderLineResult1.id());
        assertEquals(orderLineResult1.orderId(), createOrderResult.order().id());
        assertEquals(orderLineResult1.productItemId(), productItem1.getId());
        assertEquals(orderLineResult1.quantity(), orderItem1.quantity());
        assertEquals(orderLineResult1.subTotal(), orderItem1.quantity() * productItem1.getPrice());
        // order line 2
        OrderLineResult orderLineResult2 = createOrderResult.order().orderLines().get(1);
        assertNotNull(orderLineResult2.id());
        assertEquals(orderLineResult2.orderId(), createOrderResult.order().id());
        assertEquals(orderLineResult2.productItemId(), productItem2.getId());
        assertEquals(orderLineResult2.quantity(), orderItem2.quantity());
        assertEquals(orderLineResult2.subTotal(), orderItem2.quantity() * productItem2.getPrice());
    }

    @Test
    void updateAddress_success() throws Exception {
        String productItemId = UUID.randomUUID().toString();
        Address address = new Address(
                UUID.randomUUID().toString(),
                "123",
                "Test Address",
                "Test Street",
                "Test Ward",
                "Test District",
                "Test province");
        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setUserId("123");
        order.setTotalAmount(10000);
        OrderLine orderLine = new OrderLine();
        orderLine.setOrder(order);
        orderLine.setProductItemId(productItemId);
        orderLine.setId(UUID.randomUUID());
        orderLine.setQuantity(1);
        orderLine.setSubTotal(10000);
        order.setOrderLines(List.of(
                orderLine));
        Mockito.when(userAddressClient.findAddressById(address.getId())).thenReturn(Optional.of(address));
        Mockito.when(orderRepository.findOrderWithOrderLinesById(order.getId())).thenReturn(Optional.of(order));
        Mockito.when(shipmentClient.getShippingFee(Mockito.any())).thenReturn(10000);
        doNothing().when(orderRepository).save(order);
        UpdateOrderAddressCommand command = new UpdateOrderAddressCommand(
                order.getId().toString(),
                address.getId(),
                ShipProvider.GHTK);
        UpdateOrderAddressResult updateOrderAddressResult = orderUseCase.updateOrderAddress(command);
        assertNotNull(updateOrderAddressResult);
        OrderResult orderResult = updateOrderAddressResult.order();
        assertNotNull(orderResult);
        assertEquals(orderResult.id(), order.getId().toString());
        assertEquals(orderResult.shipProvider(), ShipProvider.GHTK);
        assertEquals(orderResult.shippingCost(), 10000);
        assertEquals(orderResult.recieveAddress(), address.toString());
    }

}
