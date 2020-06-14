package com.sample.util;

import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

public class RetryUtil {
    public static final int MAX_ATTEMPTS = 3;

    private static final long BACKOFF_PERIOD = 1000L;

    public static RetryTemplate buildApiRetryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy();
        simpleRetryPolicy.setMaxAttempts(MAX_ATTEMPTS);
        retryTemplate.setRetryPolicy(simpleRetryPolicy);

        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(BACKOFF_PERIOD);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        retryTemplate.setThrowLastExceptionOnExhausted(true);
        return retryTemplate;
    }
}
