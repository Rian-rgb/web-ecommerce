package com.fastcampus.web_ecommerce.service;

import java.util.function.Supplier;

public interface RateLimitingService {

    <T> T excuteWithRateLimit(String key, Supplier<T> operation);
}
