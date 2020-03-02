package com.tsf.demo.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;


@Service
public class RedisService {

    /**
     * 支持直接注入StringRedisTemplate、RedisTemplate<Object, Object>，也支持像这样RedisTemplate<String,Object>自定义bean
     */
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    public void set(String key, String value) {
        ValueOperations<Object, Object> vo = redisTemplate.opsForValue();
        vo.set(key, value);
    }

    public Object get(String key) {
        ValueOperations<Object, Object> vo = redisTemplate.opsForValue();
        return vo.get(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
