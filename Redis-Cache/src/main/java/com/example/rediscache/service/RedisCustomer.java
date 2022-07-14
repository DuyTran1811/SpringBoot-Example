package com.example.rediscache.service;

import com.example.rediscache.model.Customer;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RedisCustomer {
    private final ListOperations<String, Object> listOperations;

    public RedisCustomer(final RedisTemplate<String, Object> redisTemplate) {
        this.listOperations = redisTemplate.opsForList();
    }

    public void pushCacheCustomer(String key, List<Customer> list) {
        list.forEach(customer -> listOperations.leftPush(key, customer));
    }

    public List<Customer> getListCache(String key) {
        List<Object> list = listOperations.range(key, 0, -1);
        if (CollectionUtils.isEmpty(list)) return Collections.emptyList();
        return list.stream().map(customer -> (Customer) customer).collect(Collectors.toList());
    }

    public void deleted(String key) {
        listOperations.getOperations().delete(key);
    }
}
