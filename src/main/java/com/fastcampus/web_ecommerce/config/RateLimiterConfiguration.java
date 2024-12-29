package com.fastcampus.web_ecommerce.config;

import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RateLimiterConfiguration {

    @Value("${rate.limit.default:100}")
    private int defaultRateLimitForPeriode;

    @Value("${rate.limit.period:60}")
    private int limitRefreshPeriodInSecond;

    @Value("${rate.limit.timeout:1}")
    private int timeoutInSecond;

    @Bean
    public RateLimiterConfig rateLimiterConfig() {
        return RateLimiterConfig.custom()
                .limitForPeriod(defaultRateLimitForPeriode)
                .limitRefreshPeriod(Duration.ofSeconds(limitRefreshPeriodInSecond))
                .timeoutDuration(Duration.ofSeconds(timeoutInSecond))
                .build();
    }

    @Bean
    public RateLimiterRegistry rateLimiterRegistry(RateLimiterConfig rateLimiterConfig) {
        return RateLimiterRegistry.of(rateLimiterConfig);
    }
}
