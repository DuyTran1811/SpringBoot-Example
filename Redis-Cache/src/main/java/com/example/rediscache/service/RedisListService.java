package com.example.rediscache.service;

import com.example.rediscache.dto.Persson;
import com.example.rediscache.dto.Range;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RedisListService {
    private final ListOperations<String, Object> listOperations;

    public RedisListService(final RedisTemplate<String, Object> template) {
        this.listOperations = template.opsForList();
    }

    public void pushCache(final String key, final List<Persson> list) {
        list.forEach(persson -> listOperations.leftPush(key, persson));
    }

    public List<Persson> getListPersonRange(String key, Range range) {
        final List<Object> objects = listOperations.range(key, range.getFrom(), range.getTo());
        if (CollectionUtils.isEmpty(objects)) return Collections.emptyList();
        return objects.stream().map(p -> (Persson) p).collect(Collectors.toList());
    }

    public Persson getLastPerson(final String key) {
        Object o = listOperations.rightPop(key);
        if (o == null) return null;
        return (Persson) o;
    }

    public void trims(final String key, final Range range) {
        listOperations.trim(key, range.getFrom(), range.getTo());
    }
}
