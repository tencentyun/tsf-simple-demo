package com.tsf.demo.redis.service;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.serializer.RedisSerializer;

public interface RedisCallbackHandler {

    Object doInRedis(RedisConnection connection, RedisSerializer serializer) throws DataAccessException;
}
