package com.example.kafkaproduce;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/api")
public class Controller {
    private final KafkaProduce kafkaProduce;

    public Controller(KafkaProduce kafkaProduce) {
        this.kafkaProduce = kafkaProduce;
    }

    @GetMapping("/test")
    public String stream() {
        randomNumber();
        return "OK";
    }

    @Scheduled(fixedRate = 2000)
    private void randomNumber() {
        Random random = new Random();
        kafkaProduce.produce(random.nextInt(1000));
    }
}
