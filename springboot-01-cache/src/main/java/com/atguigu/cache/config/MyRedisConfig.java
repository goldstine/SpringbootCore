package com.atguigu.cache.config;

import com.atguigu.cache.bean.Employee;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class MyRedisConfig {

//    这里需要注释掉，否则会和  public RedisCacheManager redisCacheManager (RedisConnectionFactory redisConnectionFactory){报错
    @Bean
    public RedisTemplate<Object, Employee> empredisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Employee> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<Employee> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Employee>(Employee.class);
        template.setDefaultSerializer(jackson2JsonRedisSerializer);
        return template;
    }

    //自定义CacheManager  不使用RedisCacheManager
    //springboot2.x的写法  自定义RedisCacheManager
    //这个对于employee和department的序列化都是可以使用的     通用的自定义RedisCacheManager进行json序列化
    @Bean
    RedisCacheManager cacheManager( RedisConnectionFactory redisConnectionFactory) {
        // 使用缓存的默认配置
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        // 使用 GenericJackson2JsonRedisSerializer 作为序列化器
        config = config.serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(
                        new GenericJackson2JsonRedisSerializer()));
        RedisCacheManager.RedisCacheManagerBuilder builder =
                RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(config);

        return builder.build();
    }

//    @Bean         // springboot1.x版本
//    public RedisCacheManager employeeCacheManager(RedisTemplate<Object, Employee> empredisTemplate){
//        RedisCacheManager cacheManager = new RedisCacheManager(empredisTemplate);
//        cacheManager.setUsePrefix(true);
//        return cacheManager;
//    }


}
