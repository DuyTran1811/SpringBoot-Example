package com.kafka.orderservice.controller;

import com.kafka.domain.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
    private final KafkaTemplate<Long, Order> kafkaTemplate;
    private AtomicLong id = new AtomicLong();

    public OrderController(KafkaTemplate<Long, Order> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/test")
    public Order create(@RequestBody Order order) {
        order.setId(id.incrementAndGet());
        kafkaTemplate.send("orders", order.getId(), order);
        LOGGER.info("Sent: {}", order);
        return order;
    }
}
