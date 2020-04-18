package com.tsf.rocketmq.producer.ProducerConfiguration;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProducerConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(ProducerConfiguration.class);

    @Value("${apache.rocketmq.name-server}")
    private String namesrvAddr;

    @Value("${apache.rocketmq.producer.producerGroup}")
    private String producerGroup;

    @Bean
    public DefaultMQProducer defaultMQProducer() throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer(producerGroup);

        producer.setNamesrvAddr(namesrvAddr);

        producer.setVipChannelEnabled(false);
        producer.setRetryTimesWhenSendAsyncFailed(0);
        producer.start();
        logger.info("rocketmq producer is starting");
        return producer;
    }
}
