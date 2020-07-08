package com.tsf.rocketmq.producer.service;


import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class RocketmqService {
    private static  final Logger logger = LoggerFactory.getLogger(RocketmqService.class);

    @Autowired
    private DefaultMQProducer defaultMQProducer;

    // 使用application.properties里定义的topic属性
    @Value("${apache.rocketmq.topic}")
    private String springTopic;

    private AtomicLong id =new AtomicLong(0);

    @Scheduled(fixedDelayString = "${consumer.auto.test.interval:5000}")
    public String prepareSend() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
       return send();
    }

    public String send() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        String sentText = "rocketmq message: msg id="+id.addAndGet(1);

        Message message = new Message(springTopic,"push", sentText.getBytes());
        message.putUserProperty("traceID","1234567");

        SendResult sendResult = defaultMQProducer.send(message);

        System.out.println("消息id:"+id.get()+","+"发送状态:"+sendResult.getSendStatus());

        logger.info("消息id:"+id.get()+","+"发送状态:"+sendResult.getSendStatus());

        return sendResult.getMsgId();
    }
}
