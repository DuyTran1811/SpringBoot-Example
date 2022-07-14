package com.example.rediscache.controller;

import com.example.rediscache.dto.Persson;
import com.example.rediscache.service.RedisValueService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/person")
public class PersonController {
    private final RedisValueService redisValueService;

    public PersonController(final RedisValueService redisValueService) {
        this.redisValueService = redisValueService;
    }

//    @PostMapping("/push")
//    public void pushCache(@RequestBody final Persson persson) {
//        redisValueService.pushCacheSetTime(persson.getId(), 5000, persson);
//    }

    @GetMapping("/{id}")
    public Persson getPerSon(@PathVariable("id") String id) {
        return (Persson) redisValueService.getValue(id);
    }

    @DeleteMapping("/{id}")
    public void deleted(@PathVariable("id") String id) {
        redisValueService.deleted(id);
    }
}
