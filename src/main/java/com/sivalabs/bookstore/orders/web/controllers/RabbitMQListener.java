package com.sivalabs.bookstore.orders.web.controllers;

import com.sivalabs.bookstore.orders.web.controllers.RabbitMQDemoController.MyPayload;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
class RabbitMQListener {

    @RabbitListener(queues = "${orders.new-orders-queue}")
    public void handleNewOrder(MyPayload payload) {
        System.out.println("Received new order: " + payload.content());
    }

    @RabbitListener(queues = "${orders.delivered-orders-queue}")
    public void handleDeliveredOrder(MyPayload payload) {
        System.out.println("Received delivered order: " + payload.content());
    }
}
