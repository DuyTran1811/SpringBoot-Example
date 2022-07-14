package com.example.rediscache.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisValueService {
    private final ValueOperations<String, Object> valueOperations;

    public RedisValueService(final RedisTemplate<String, Object> template) {
        this.valueOperations = template.opsForValue();
    }

    public void pushCache(final String key, final Object data) {
        valueOperations.set(key, data);
    }

//    public void pushCacheSetTime(final String key, int time, final Object data) {
//        valueOperations.set(key, data, time, TimeUnit.MILLISECONDS);
//    }

    public Object getValue(final String key) {
        return valueOperations.get(key);
    }

    public void deleted(final String key) {
        valueOperations.getOperations().delete(key);
    }

}
