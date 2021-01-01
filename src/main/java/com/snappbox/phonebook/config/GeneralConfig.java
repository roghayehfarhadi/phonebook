package com.snappbox.phonebook.config;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

@EnableScheduling
@Configuration
public class GeneralConfig {

    @Value("${github.maxAttempts}")
    private int maxAttempts;

    @Value("${github.waitDuration}")
    private Long waitDuration;

    @Value("${github.retry}")
    private String retry;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Retry retry() {
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(5)
                .waitDuration(Duration.of(5, SECONDS))
                .build();
        return Retry.of("github", config);
    }
}
