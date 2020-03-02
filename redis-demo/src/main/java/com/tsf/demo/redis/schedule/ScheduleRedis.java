package com.tsf.demo.redis.schedule;

import com.tsf.demo.redis.pojo.Man;
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

    @Scheduled(fixedDelayString = "${consumer.auto.test.interval:5000}")
    public void doWork() throws InterruptedException, Exception{

        String testKey = "objectKey";

        Man man = new Man("张三", 20);
        redisService.setObject(testKey,man);
        Thread.sleep(1);
        Man manFromRedis = (Man)redisService.get(testKey);

        LOG.info("Redis auto test,get from redis with key: {}, result is: {}",testKey,manFromRedis);
        Thread.sleep(1);
        redisService.delete(testKey);

        redisService.updateDriverInfo("aaa", 123L, "bbb");
    }
}
