package com.tsf.demo.cmq.producer.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * Created by chazling on 2019/5/23.
 */
public interface SinkSender {

    String OUTPUT = "output";

    @Output(SinkSender.OUTPUT)
    MessageChannel channel();

}
