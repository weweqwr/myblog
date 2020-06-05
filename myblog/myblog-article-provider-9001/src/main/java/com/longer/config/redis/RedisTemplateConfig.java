package com.longer.config.redis;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RedisTemplateConfig {
    //order
    @Value("${spring.redis.redis1.host}")
    private String orderHost;

    @Value("${spring.redis.redis1.port}")
    private String orderPort;

    @Value("${spring.redis.redis1.password}")
    private String orderPassword;
    @Value("${spring.redis.redis1.timeout}")
    private String orderTimeout;


    //user
    @Value("${spring.redis.redis2.host}")
    private String userHost;

    @Value("${spring.redis.redis2.port}")
    private String userPort;

    @Value("${spring.redis.redis2.password}")
    private String userPassword;
    @Value("${spring.redis.redis2.timeout}")
    private String userTimeout;


    private static final int MAX_IDLE = 200; //最大空闲连接数
    private static final int MAX_TOTAL = 1024; //最大连接数
    private static final long MAX_WAIT_MILLIS = 10000; //建立连接最长等待时间


    //配置工厂
    public RedisConnectionFactory connectionFactory(String host, int port, String password, int maxIdle,
                                                    int maxTotal, long maxWaitMillis, int index) {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(host);
        jedisConnectionFactory.setPort(port);

        if (!StringUtils.isEmpty(password)) {
            jedisConnectionFactory.setPassword(password);
        }

        if (index != 0) {
            jedisConnectionFactory.setDatabase(index);
        }

        jedisConnectionFactory.setPoolConfig(poolConfig(maxIdle, maxTotal, maxWaitMillis, false));
        jedisConnectionFactory.afterPropertiesSet();
        return jedisConnectionFactory;
    }

    //连接池配置
    public JedisPoolConfig poolConfig(int maxIdle, int maxTotal, long maxWaitMillis, boolean testOnBorrow) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMaxTotal(maxTotal);
        poolConfig.setMaxWaitMillis(maxWaitMillis);
        poolConfig.setTestOnBorrow(testOnBorrow);
        return poolConfig;
    }


    //------------------------------------
    @Bean(name = "redisRedis1Template")
    public StringRedisTemplate redisRedis1Template() {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(
                connectionFactory(orderHost, Integer.parseInt(orderPort), orderPassword, MAX_IDLE, MAX_TOTAL, MAX_WAIT_MILLIS, 0));
        return template;
    }

    //------------------------------------
    @Bean(name = "redisRedis2Template")
    public StringRedisTemplate redisRedis2Template() {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(
                connectionFactory(userHost, Integer.parseInt(userPort), userPassword, MAX_IDLE, MAX_TOTAL, MAX_WAIT_MILLIS, 0));
        return template;
    }

    //------------------------------------
    @Bean(name = "redisRedis3Template")
    public StringRedisTemplate redisRedis3Template() {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setKeySerializer(new StringRedisSerializer());
        //redis反序列化不成功 得使用JdkSerializationRedisSerializer
        template.setValueSerializer(new JdkSerializationRedisSerializer());
        template.setConnectionFactory(
                connectionFactory(orderHost, Integer.parseInt(orderPort), orderPassword, MAX_IDLE, MAX_TOTAL, MAX_WAIT_MILLIS, 0));
        return template;
    }

    //------------------------------------
    @Bean(name = "redisRedis4Template")
    public StringRedisTemplate redisRedis4Template() {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new JdkSerializationRedisSerializer());
        template.setConnectionFactory(
                connectionFactory(userHost, Integer.parseInt(userPort), userPassword, MAX_IDLE, MAX_TOTAL, MAX_WAIT_MILLIS, 0));
        return template;
    }



}
