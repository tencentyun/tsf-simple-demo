package com.tsf.demo.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.Objects;


@Service
public class RedisService{

    /**
     * 支持直接注入StringRedisTemplate、RedisTemplate<Object, Object>，也支持像这样RedisTemplate<String,Object>自定义bean
     */
    @Autowired
    @Qualifier("accessAuthObjectRedisTemplate")
    private RedisTemplate<String,Object> redisTemplate;

    @Qualifier("redisTemplateInit")
    @Autowired
    private RedisTemplate redisTemplateInit;

    public void set(String key, String value) {
        ValueOperations<String,Object> vo = redisTemplate.opsForValue();
        vo.set(key, value);
    }

    public void setObject(String key, Object object){
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        operations.set(key,object);
    }

    public Object get(String key) {
        ValueOperations<String,Object> vo = redisTemplate.opsForValue();
        return vo.get(key);
    }


    public void delete(String key) {
        redisTemplate.delete(key);
    }


    public boolean updateDriverInfo(String key, long driverId, String str) {
        boolean executeResult = execute((connection, serializer) -> {
            Jedis jedis = (Jedis) connection.getNativeConnection();
            Long result = jedis.hset(key, String.valueOf(driverId), str);
            if (Objects.isNull(result)) {
                return false;
            }

//            String hget = jedis.hget(key, String.valueOf(driverId));
//            System.out.println(hget);
            // If the field already exists, and the HSET just produced an update of the value, 0 is returned,
            // otherwise if a new field is created 1 is returned.
            return result >= 0;
        });


        return executeResult;
    }

    public boolean execute(RedisCallbackHandler callbackHandler){
        return (Boolean) redisTemplateInit.execute((RedisCallback<Boolean>) connection ->
                (Boolean) callbackHandler.doInRedis(connection, getKeySerializer()));
    }

    public RedisSerializer getKeySerializer() {
        return redisTemplate.getKeySerializer();
    }

}
