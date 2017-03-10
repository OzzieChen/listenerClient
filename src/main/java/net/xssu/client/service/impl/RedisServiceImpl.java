package net.xssu.client.service.impl;

import net.xssu.client.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

public class RedisServiceImpl implements IRedisService {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public String getPatternString(String hKey, String patternId) {
        Object result =  stringRedisTemplate.opsForHash().get(hKey, patternId);
        return result.toString();
    }
}
