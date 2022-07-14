package com.example.rediscache.controller;

import com.example.rediscache.dto.Persson;
import com.example.rediscache.dto.Range;
import com.example.rediscache.service.RedisListService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/per")
public class PersonListController {
    private final RedisListService redisListService;

    public PersonListController(RedisListService redisListService) {
        this.redisListService = redisListService;
    }

    @PostMapping("addList/{key}")
    public void pushCache(@PathVariable String key, @RequestBody List<Persson> persson) {
        redisListService.pushCache(key, persson);
    }

    @PostMapping("listKay/{key}")
    public List<Persson> getPerSon(@PathVariable("key") String key, @RequestBody Range range) {
        return redisListService.getListPersonRange(key, range);
    }

    @GetMapping("/listKay/last/{key}")
    public Persson getLastPerson(@PathVariable("key") String key) {
        return redisListService.getLastPerson(key);
    }

    @DeleteMapping("/list-trim/{key}")
    public void trims(@PathVariable("key") String key, @RequestBody Range range) {
        redisListService.trims(key, range);
    }
}
