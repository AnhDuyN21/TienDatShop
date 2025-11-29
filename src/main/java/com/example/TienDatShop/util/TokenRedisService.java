package com.example.TienDatShop.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenRedisService {
    private final RedisTemplate<String, String> redisTemplate;

    private static final String TOKEN_PREFIX = "user:token:";
    private static final String TOKEN_MAPPING = "token:";
    private static final long TOKEN_EXPIRE_TIME = 10 * 60 * 60;


    public void saveToken(Long userId, String newToken) {
        String userKey = TOKEN_PREFIX + userId;
        String oldToken = redisTemplate.opsForValue().get(userKey);
        if (oldToken != null && !oldToken.isEmpty()) {
            redisTemplate.delete(TOKEN_MAPPING + oldToken);
        }
        redisTemplate.opsForValue().set(userKey, newToken, TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(TOKEN_MAPPING + newToken, userId.toString(), TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);
    }

    public boolean isValidToken(Long userId, String token) {
        String userKey = TOKEN_PREFIX + userId;
        String storedToken = redisTemplate.opsForValue().get(userKey);
        return token.equals(storedToken);
    }

    public Long getUserIdByToken(String token) {
        String userIdStr = redisTemplate.opsForValue().get(TOKEN_MAPPING + token);
        return userIdStr != null ? Long.parseLong(userIdStr) : null;
    }

    public void removeToken(Long userId, String token) {
        redisTemplate.delete(TOKEN_PREFIX + userId);
        redisTemplate.delete(TOKEN_MAPPING + token);
    }

    public void renewToken(Long userId, String token) {
        String userKey = TOKEN_PREFIX + userId;
        redisTemplate.expire(userKey, TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);
        redisTemplate.expire(TOKEN_MAPPING + token, TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);
    }
}
