package com.kafka.orderservice.service;

import com.kafka.domain.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderManageService {
    public Order confirm(Order orderPayment, Order orderStock) {
        Order order = new Order(orderPayment.getId(),
                orderPayment.getCustomerId(),
                orderPayment.getProductId(),
                orderPayment.getProductCount(),
                orderPayment.getPrice());
        if (orderPayment.getStatus().equals("ACCEPT") && orderStock.getStatus().equals("ACCEPT")) {
            order.setStatus("CONFIRMED");
        } else if (orderPayment.getStatus().equals("REJECT") && orderStock.getStatus().equals("REJECT")) {
            order.setStatus("REJECTED");
        } else if (orderPayment.getStatus().equals("REJECT") || orderStock.getStatus().equals("REJECT")) {
            String source = orderPayment.getStatus().equals("REJECT") ? "PAYMENT" : "STOCK";
            order.setStatus("ROLLBACK");
            order.setSource(source);
        }
        return order;
    }

}
