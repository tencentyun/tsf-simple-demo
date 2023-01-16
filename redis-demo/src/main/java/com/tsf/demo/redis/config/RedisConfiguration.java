package com.tsf.demo.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;


@Configuration
@EnableConfigurationProperties(value = { RedisProperties.class })
public class RedisConfiguration {

    /**
     * 读取配置文件里 的redis配置
     */
    @Value("${redis.cache.database}")
    private Integer cacheDatabaseIndex;

    /**
     * sessiondb的数据库索引
     */
    @Value("${redis.session.database}")
    private Integer sessionDatabaseIndex;

    @Value("${redis.host}")
    private String hostName;

    @Value("${redis.port}")
    private Integer port;

    @Value("${redis.password}")
    private String password;

    @Value("${redis.lettuce.pool.max-idle}")
    private Integer maxIdle;

    @Value("${redis.lettuce.pool.min-idle}")
    private Integer minIdle;

    @Value("${redis.lettuce.pool.max-active}")
    private Integer maxActive;

    @Value("${redis.lettuce.pool.max-wait}")
    private Long maxWait;

    @Value("${redis.timeout}")
    private Long timeOut;

    @Value("${redis.lettuce.shutdown-timeout}")
    private Long shutdownTimeOut;

    /**
     * 配置 Jackson2JsonRedisSerializer 序列化器，在配置 redisTemplate需要用来做k,v的
     * 序列化器
     */
    static Jackson2JsonRedisSerializer getJackson2JsonRedisSerializer(){
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = null;
        jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        return jackson2JsonRedisSerializer;
    }

    static final Jackson2JsonRedisSerializer jackson2JsonRedisSerializer =
            getJackson2JsonRedisSerializer();

    private  LettuceConnectionFactory createLettuceConnectionFactory(
            int dbIndex, String hostName, int port, String password,
            int maxIdle,int minIdle,int maxActive,
            Long maxWait, Long timeOut,Long shutdownTimeOut){

        //redis配置
        RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration(hostName,port);
        ((RedisStandaloneConfiguration) redisConfiguration).setDatabase(dbIndex);
        ((RedisStandaloneConfiguration) redisConfiguration).setPassword(RedisPassword.of(password));
        //连接池配置
        GenericObjectPoolConfig genericObjectPoolConfig =
                new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxIdle(maxIdle);
        genericObjectPoolConfig.setMinIdle(minIdle);
        genericObjectPoolConfig.setMaxTotal(maxActive);
        genericObjectPoolConfig.setMaxWaitMillis(maxWait);

        //redis客户端配置
        LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder
                builder =  LettucePoolingClientConfiguration.builder().
                commandTimeout(Duration.ofMillis(timeOut));

        builder.shutdownTimeout(Duration.ofMillis(shutdownTimeOut));
        builder.poolConfig(genericObjectPoolConfig);
        LettuceClientConfiguration lettuceClientConfiguration = builder.build();

        //根据配置和客户端配置创建连接
        LettuceConnectionFactory lettuceConnectionFactory = new
                LettuceConnectionFactory(redisConfiguration,lettuceClientConfiguration);


        return lettuceConnectionFactory;
    }

    @Bean(value = "cacheDatabaseIndex")
    public LettuceConnectionFactory lettuceConnectionFactory(){
        return createLettuceConnectionFactory(cacheDatabaseIndex,hostName,port,password,maxIdle,minIdle,maxActive,maxWait,timeOut,shutdownTimeOut);
    }

    /**
     *  配置 cache RedisTemplate
     * @return  RedisTemplate<String,Serializable>r
     */
    @Bean(value="cacheRedisTemplate")
    public RedisTemplate<String,Object> getCacheRedisTemplate(@Qualifier("cacheDatabaseIndex") LettuceConnectionFactory lettuceConnectionFactory){
        //创建客户端连接
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        /**
         * 使用 String 作为 Key 的序列化器,使用 Jackson 作为 Value 的序列化器
         */
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        redisTemplate.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value序列化方式采用jackson
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    @Bean(value = "sessionDatabaseIndex")
    @Primary
    public LettuceConnectionFactory sessionLettuceConnectionFactory(){
        return createLettuceConnectionFactory(sessionDatabaseIndex,hostName,port,password,maxIdle,minIdle,maxActive,maxWait,timeOut,shutdownTimeOut);
    }

    /**
     *  配置Session RedisTemplate
     * @return  RedisTemplate<String,Serializable>
     */
    @Bean(value="sessionRedisTemplate")
    public RedisTemplate<String,Object> getSessionRedisTemplate(@Qualifier("sessionDatabaseIndex") LettuceConnectionFactory lettuceConnectionFactory){
        //创建客户端连接
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        /**
         * 使用 String 作为 Key 的序列化器,使用 Jackson 作为 Value 的序列化器
         */
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        redisTemplate.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value序列化方式采用jackson
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
