package com.sunlight.zuul.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("redisService")
public class RedisService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    public void setStringValue(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }
    public String getStringValue(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }
}
