package com.sivalabs.bookstore.orders.domain;

import com.sivalabs.bookstore.orders.domain.models.CreateOrderRequestDto;
import com.sivalabs.bookstore.orders.domain.models.CreateOrderResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;

    OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public CreateOrderResponse createOrder(@Valid CreateOrderRequestDto request, String username) {
        OrderEntity newOrder = OrderMapper.convertToOrderEntity(request);
        newOrder.setUsername(username);
        OrderEntity savedOrder = this.orderRepository.save(newOrder);
        log.info("Order created with orderNumber={}", savedOrder.getOrderNumber());
        return new CreateOrderResponse(savedOrder.getOrderNumber());
    }
}
