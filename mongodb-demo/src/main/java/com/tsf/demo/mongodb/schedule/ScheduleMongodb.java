package com.tsf.demo.mongodb.schedule;

import com.tsf.demo.mongodb.dto.User;
import com.tsf.demo.mongodb.service.MongoDbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class ScheduleMongodb {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleMongodb.class);

    @Autowired
    private MongoDbService mongoDbService;

    private static  User user = new User((long)100,"mongodb-auto-test",26,"man","深圳");

    private static  User userUpdater = new User((long)100,"mongodb-auto-test",23,"girl","上海");

    private static HttpEntity<User> request = new HttpEntity<>(user);

    @Scheduled(fixedDelayString = "${consumer.auto.test.interval:5000}")
    public void doWork() throws InterruptedException, Exception{

        mongoDbService.save(user);
        findResult();

        mongoDbService.update(userUpdater);
        findResult();

        mongoDbService.deleteById(100L);
    }

    private void findResult(){
        User userById = mongoDbService.findById(100L);
        logger.info("find user by id: {},result is: {}",100,userById);
        List<User> usersList = mongoDbService.findByName("mongodb-auto-test");
        for (User user1 : usersList) {
            logger.info("find user by name: {},result is: {}","mongodb-auto-test",user1);
        }

        List<User> all = mongoDbService.findAll();
        Iterator<User> iterator = all.iterator();
        while (iterator.hasNext()){
            logger.info("find all, result is: {}",iterator.next());
        }
    }

}
