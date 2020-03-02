package com.tsf.kafka.consumer.cumsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class KafkaReceiver {
    private static final Logger logger = LoggerFactory.getLogger(KafkaReceiver.class);



    @KafkaListener(topics = "zhizhang3")
    public void listen(ConsumerRecord<?, ?> record) {

        Optional<?> kafkaMessage = Optional.ofNullable(record.value());

        try{
            Thread.sleep(10);
        }catch (Exception e){

        }

        if (kafkaMessage.isPresent()) {

            Object message = kafkaMessage.get();

            logger.info("----------------- record =" + record);
            logger.info("------------------ message =" + message);
        }

    }
}
