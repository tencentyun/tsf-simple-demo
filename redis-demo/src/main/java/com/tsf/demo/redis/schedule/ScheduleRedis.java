package com.tsf.demo.redis.schedule;

import com.tsf.demo.redis.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleRedis {
    private static final Logger LOG = LoggerFactory.getLogger(ScheduleRedis.class);

    @Autowired
    private RedisService redisService;

    @Scheduled(fixedDelayString = "${consumer.auto.test.interval:10000}")
    public void doWork() throws InterruptedException, Exception{
        String key ="auto-test-key";
        String value = "1111111222222";
        redisService.set(key,value);

        Object o = redisService.get(key);
        LOG.info("Redis auto test,get from redis with key: {}, result is: {}",key,o);

        redisService.delete(key);
    }
}
