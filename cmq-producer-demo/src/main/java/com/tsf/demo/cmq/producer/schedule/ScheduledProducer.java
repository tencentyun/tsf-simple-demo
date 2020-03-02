package com.tsf.demo.cmq.producer.schedule;

import com.tsf.demo.cmq.producer.messaging.CmqService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledProducer {
	private static final Logger LOG = LoggerFactory.getLogger(ScheduledProducer.class);

	private static int index = 1;

	@Autowired
	private CmqService cmqService;

	@Scheduled(fixedDelayString = "${consumer.auto.test.interval:10000}")
	public void doWork() throws InterruptedException, Exception {
		String username = "test-user-" + index;
		LOG.info("cmq-producer-demo auto test, create user: [" + username + "]");

		LOG.info("cmq-producer-demo auto test, send username to mq: [" + username + "]");
		cmqService.handle(username);

		index++;
		if (index > 9) index = 1;
	}
}