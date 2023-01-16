package com.tsf.demo.mysql.schedule;

import com.tsf.demo.mysql.dao.TsfCountDao;
import com.tsf.demo.mysql.dao.TsfMybatisUserDao;
import com.tsf.demo.mysql.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.tsf.demo.mysql.dao.TsfUserDao;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ScheduledDemo {
	private static final Logger LOG = LoggerFactory.getLogger(ScheduledDemo.class);

	private static int index = 1;

	@Autowired
	private TsfUserDao userDao;

	@Autowired
	private TsfCountDao countDao;

	@Autowired
	private TsfMybatisUserDao mybatisUserDao;

	@Scheduled(fixedDelayString = "${consumer.auto.test.interval:10000}")
	public void doWork() throws InterruptedException, Exception {
		String username = "test-user-" + index;
		LOG.info("mysql-demo auto test, create user: [" + username + "]");

		LOG.info("mysql-demo auto test, save user into mysql: [" + username + "]");
		userDao.create(username);

		Thread.sleep(3000);

		String token = userDao.query(username);
		LOG.info("mysql-demo auto test, query user token from mysql: [" + token + "]");

		Thread.sleep(3000);

		LOG.info("mysql-demo auto test, save mybatis user into mysql: [" + username + "]");
		mybatisUserDao.insertUser(username, "token:" + username);

        Thread.sleep(3000);

		User user = mybatisUserDao.findUserByName(username);
		LOG.info("mysql-demo auto test, query mybatis user token from mysql: [" + user.getUser_token() + "]");

		Thread.sleep(3000);

		countDao.insert();
		LOG.info("mysql-demo auto test, insert count into mysql");

		index++;
		if (index > 9) index = 1;

		//测试事务时启用
        //throw new RuntimeException("test-exception");
	}
}