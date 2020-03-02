package com.tsf.demo.cmq.producer.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * Created by chazling on 2019/5/23.
 */
@Service
public class CmqService {

    @Autowired
    private Source source;

    public void handle(String value) {
        source.output().send(MessageBuilder.withPayload(value).build());
    }

}