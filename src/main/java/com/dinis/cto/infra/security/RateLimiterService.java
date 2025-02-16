package com.dinis.cto.infra.security;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimiterService {

    private final Map<String, Bucket> loginAttemptsBuckets = new ConcurrentHashMap<>();

    public boolean allowLoginAttempt(String username) {
        Bucket bucket = loginAttemptsBuckets.computeIfAbsent(username, this::createNewBucket);
        return bucket.tryConsume(1);
    }

    private Bucket createNewBucket(String username) {
        // Define a política de rate limiting: 3 tentativas a cada 20 segundos
        Bandwidth limit = Bandwidth.classic(3, Refill.intervally(3, Duration.ofSeconds(20)));
        return Bucket4j.builder().addLimit(limit).build();
    }

    public void resetLoginAttempts(String username) {
        loginAttemptsBuckets.remove(username);
    }
}
