package com.example.kafkaconsumer.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    @KafkaListener(topics = "myTopic", groupId = "group-id")
    public void kafkaListener(String message) {
        System.out.println(message);
    }
}
