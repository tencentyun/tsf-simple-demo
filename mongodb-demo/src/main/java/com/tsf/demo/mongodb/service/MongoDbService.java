package com.tsf.demo.mongodb.service;

import com.tsf.demo.mongodb.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

@Component
public class MongoDbService {

    @Autowired
    private MongoTemplate mongoTemplate;


    public void save(User user) {
        mongoTemplate.save(user);
    }


    public void update(User user) {
        //创建更新条件
        Query query = new Query();
        Criteria criteria = Criteria.where("id").is(user.getId());
        query.addCriteria(criteria);

        //创建更新对象
        Update update = new Update();
        update.set("name", user.getName());
        update.set("age", user.getAge());
        update.set("gender", user.getGender());
        update.set("address", user.getAddress());

        //更新所有符合条件的记录
        mongoTemplate.updateMulti(query, update, User.class);
    }


    public void deleteById(Long id) {
        //创建删除条件
        Query query = new Query();
        Criteria criteria = Criteria.where("id").is(id);
        query.addCriteria(criteria);
        mongoTemplate.remove(query, User.class);
    }


    public User findById(Long id) {
        Query query = new Query();
        Criteria criteria = Criteria.where("id").is(id);
        query.addCriteria(criteria);
        return mongoTemplate.findOne(query, User.class);
    }


    public List<User> findAll() {
        return mongoTemplate.findAll(User.class);
    }


    public List<User> findByName(String name) {
        Query query = new Query();
        //模糊查询，不区分大小写
        Pattern pattern = Pattern.compile("^.*" + name + ".*$", Pattern.CASE_INSENSITIVE);
        Criteria criteria = Criteria.where("name").regex(pattern);
        query.addCriteria(criteria);
        return mongoTemplate.find(query, User.class);
    }
}
