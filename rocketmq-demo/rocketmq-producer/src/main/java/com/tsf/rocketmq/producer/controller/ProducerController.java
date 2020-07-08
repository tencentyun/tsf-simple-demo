package com.tsf.rocketmq.producer.controller;

import com.tsf.rocketmq.producer.service.RocketmqService;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/producer")
public class ProducerController {

    @Autowired
    private RocketmqService rocketmqService;

    @ResponseBody
    @RequestMapping("/send")
    public String sendMessage() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        return rocketmqService.prepareSend();
    }
}
