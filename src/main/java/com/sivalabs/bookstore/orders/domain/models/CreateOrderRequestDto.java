package com.sivalabs.bookstore.orders.domain.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;

public record CreateOrderRequestDto(
        @NotEmpty(message = "Order must contain at least one item") Set<OrderBook> books,
        @Valid Customer customer,
        @Valid DeliveryAddress deliveryAddress) {}
