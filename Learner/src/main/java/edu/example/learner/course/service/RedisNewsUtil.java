package edu.example.learner.course.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisNewsUtil {
    private final StringRedisTemplate stringRedisTemplate;

    // key 값을 받으면 ValueOperations 객체를 생성
    public String getData(String key) {
        //redis를 문자열 형식으로 변경하는 클래스
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    // 데이터를 가져오는 로직 + 키의 만료일자 구현
    public void settDateExpire(String key, String value, long duration) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key, value, expireDuration);
    }

}
