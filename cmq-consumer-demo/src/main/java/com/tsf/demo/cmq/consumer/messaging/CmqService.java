package com.tsf.demo.cmq.consumer.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.stereotype.Service;

/**
 * Created by chazling on 2019/5/23.
 */
@Service
public class CmqService {

    private static final Logger LOG = LoggerFactory.getLogger(CmqService.class);

    @StreamListener(Processor.INPUT)
    public void handle(String value) {
        LOG.info("consumer-demo auto test, get username from mq: [" + value + "]");
        return;
    }

}