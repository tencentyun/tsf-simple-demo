package com.tsf.demo.redis.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleRedis {
    private static final Logger LOG = LoggerFactory.getLogger(ScheduleRedis.class);

    @Autowired
    private RedisTemplate cacheRedisTemplate;

    @Autowired
    private RedisTemplate sessionRedisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Scheduled(fixedDelayString = "${consumer.auto.test.interval:1000}")
    public void doWork() throws InterruptedException, Exception{
        String key ="auto-test-key";
        String value = "1111111222222";

        cacheRedisTemplate.opsForValue().set(key, value);
        sessionRedisTemplate.opsForValue().set(key, value);
        String cacheRedisTemplateResult = cacheRedisTemplate.opsForValue().get(key).toString();
        String sessionRedisTemplateResult =sessionRedisTemplate.opsForValue().get(key).toString();

        LOG.info("cacheRedisTemplate result:{}, sessionRedisTemplate result: {}",cacheRedisTemplateResult,sessionRedisTemplateResult);



        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.set(key,value);
        String result = operations.get(key);
        LOG.info("string redis key: {}, result is: {}",key,result);
        stringRedisTemplate.delete(key);

    }
}
