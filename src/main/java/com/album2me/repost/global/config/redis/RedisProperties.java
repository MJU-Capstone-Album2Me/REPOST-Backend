package com.album2me.repost.global.config.redis;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class RedisProperties {
    private final String host;
    private final int port;

    public RedisProperties(
            @Value("${spring.data.redis.host}") String host,
            @Value("${spring.data.redis.port}") int port) {
        this.host = host;
        this.port = port;
    }
}
