package com.example.kafkapublisher.kafka;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class KafkaPublisher {
    private final KafkaTemplate<String, String> kafkaTemplate;
    public void sendMessage() {
        String a = "Test = ";
        for (int i = 0; i < 1000; i++) {
            kafkaTemplate.send("myTopic", a + i);
        }
    }
}
