package com.tsf.kafka.producer.schedule;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tsf.kafka.producer.beans.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ScheduleKafkaSender {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleKafkaSender.class);
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private Gson gson = new GsonBuilder().create();

    private static final AtomicLong id = new AtomicLong(0);

    @Scheduled(fixedDelayString = "${consumer.auto.test.interval:5000}")
    public void doWork() throws Exception{

        Message message = new Message();
        message.setId(id.getAndAdd(1));
        message.setMsg(UUID.randomUUID().toString());
        message.setSendTime(new Date());
        logger.info("+++++++++++++++++++++  message = {}", gson.toJson(message));

        Thread.sleep(5);

        kafkaTemplate.send("zhizhang3", gson.toJson(message));
    }
}
