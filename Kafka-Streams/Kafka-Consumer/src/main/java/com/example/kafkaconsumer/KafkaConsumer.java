package com.example.kafkaconsumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    @KafkaListener(topics = "even-number-topic", groupId = "group-stream")
    public void kafkaListener(String messenger) {
        System.out.println(messenger);
    }
}
