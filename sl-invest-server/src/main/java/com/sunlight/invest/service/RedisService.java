package com.sunlight.invest.service;

import com.sunlight.invest.vo.MonitorPriceVo;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 * @package com.sunlight.invest.service
 * @date 2019/3/20
 */
@Service("redisService")
public class RedisService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public void setMap(String key, Map<String, String> data) {
        stringRedisTemplate.opsForHash().putAll(key, data);
    }

    public Map<Object, Object> getObjectMap(String key) {
        return stringRedisTemplate.opsForHash().entries(key);
    }

    public void putHash(String key, String hashKey, String hashValue) {
        stringRedisTemplate.opsForHash().put(key, hashKey, hashValue);
    }

    public List<String> getArray(String key) {
        return stringRedisTemplate.opsForList().range(key, 0, -1);
    }

    public void putArray(String key, String value) {
        stringRedisTemplate.opsForList().leftPush(key, value);
    }

    public void removeHash(String key, String hashKey) {
        stringRedisTemplate.opsForHash().delete(key, hashKey);
    }

    public Object getMapValue(String key, String hashKey) {
        return stringRedisTemplate.opsForHash().get(key, hashKey);
    }

    public void setString(String key, String value) {
        stringRedisTemplate.opsForValue().set(key,value);
    }

    public void setString(String key, String value, long overTime, TimeUnit timeUnit) {
        stringRedisTemplate.opsForValue().set(key,value, overTime, timeUnit);
    }

    public String getString(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public List<String> getStrList(String pattern) {
        Set<String> keys = stringRedisTemplate.keys(pattern+"*");
        if (keys != null) {
            return stringRedisTemplate.opsForValue().multiGet(keys);
        }
        return new ArrayList<>();
    }
}
