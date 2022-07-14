package com.example.kafkaproduce;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component

public class KafkaProduce {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProduce(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void produce(Integer randomNumber) {
        String s = "Odd";
        if (randomNumber % 2 == 0) s = "Even";
        System.out.println("Produced number: " + randomNumber);
        kafkaTemplate.send("number-topic", s, String.valueOf(randomNumber));
    }
}
