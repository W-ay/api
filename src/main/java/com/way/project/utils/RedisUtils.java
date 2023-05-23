package com.way.project.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;


/**
 * Redis工具类
 *
 * @author Way
 */
@Component
public class RedisUtils {
    private final StringRedisTemplate stringRedisTemplate;
    private final RedisTemplate redisTemplate;

    public RedisUtils(StringRedisTemplate stringRedisTemplate, RedisTemplate redisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.redisTemplate = redisTemplate;
    }

    public  StringRedisTemplate getStringRedisTemplate(){
        return stringRedisTemplate;
    }

    public  RedisTemplate getRedisTemplate(){
        return redisTemplate;
    }
}
