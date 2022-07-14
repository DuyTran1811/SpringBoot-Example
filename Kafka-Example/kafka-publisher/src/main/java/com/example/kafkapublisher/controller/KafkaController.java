package com.example.kafkapublisher.controller;

import com.example.kafkapublisher.kafka.KafkaPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class KafkaController {
    private final KafkaPublisher kafkaPublisher;

    public KafkaController(KafkaPublisher kafkaPublisher) {
        this.kafkaPublisher = kafkaPublisher;
    }

    @GetMapping("/push")
    public String push() {
        kafkaPublisher.sendMessage();
        return "OK";
    }
}
