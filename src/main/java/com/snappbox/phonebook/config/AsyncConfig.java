package com.snappbox.phonebook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync
public class AsyncConfig {

    public static final String THREAD_POOL_GITHUB = "githubThreadPool";

    @Bean(name = THREAD_POOL_GITHUB)
    public Executor threadPoolPushExecutor() {
        return Executors.newFixedThreadPool(100);
    }
}
