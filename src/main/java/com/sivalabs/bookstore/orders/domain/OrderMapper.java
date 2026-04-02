package com.sivalabs.bookstore.orders.domain;

import com.sivalabs.bookstore.orders.domain.models.CreateOrderRequestDto;
import com.sivalabs.bookstore.orders.domain.models.OrderBook;
import com.sivalabs.bookstore.orders.domain.models.OrderStatus;
import jakarta.validation.Valid;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

class OrderMapper {

    public static OrderEntity convertToOrderEntity(@Valid CreateOrderRequestDto request) {
        OrderEntity newOrder = new OrderEntity();
        newOrder.setOrderNumber(UUID.randomUUID().toString());
        newOrder.setOrderStatus(OrderStatus.NEW);
        newOrder.setCustomer(request.customer());
        newOrder.setDeliveryAddress(request.deliveryAddress());
        Set<OrderItemEntity> orderItems = new HashSet<>();
        for (OrderBook book : request.books()) {
            OrderItemEntity orderedBook = new OrderItemEntity();
            orderedBook.setCode(book.code());
            orderedBook.setName(book.name());
            orderedBook.setPrice(book.price());
            orderedBook.setQuantity(book.quantity());
            orderedBook.setOrder(newOrder);
            orderItems.add(orderedBook);
        }
        newOrder.setItems(orderItems);
        return newOrder;
    }
}
