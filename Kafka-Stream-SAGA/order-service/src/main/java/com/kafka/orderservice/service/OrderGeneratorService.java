package com.kafka.orderservice.service;

import com.kafka.domain.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class OrderGeneratorService {
    private static final Random RAND = new Random();
    private final AtomicLong id = new AtomicLong();
    private Executor executor;
    private final KafkaTemplate<Long, Order> kafkaTemplate;

    public OrderGeneratorService(KafkaTemplate<Long, Order> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Async
    public void generate() {
        for (int i = 0; i < 100; i++) {
            int x = RAND.nextInt(5) + 1;
            Order o = new Order(id.incrementAndGet(), RAND.nextLong() + 1, RAND.nextLong() + 1, "NEW");
            o.setPrice(100 * x);
            o.setProductCount(x);
            kafkaTemplate.send("orders", o.getId(), o);
        }
    }
}
