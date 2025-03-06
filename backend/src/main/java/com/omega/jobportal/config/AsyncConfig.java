package com.omega.jobportal.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
@RequiredArgsConstructor
public class AsyncConfig implements AsyncConfigurer {
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public Executor getAsyncExecutor() {
        int minPoolSize = 3;
        int maxPoolSize = 4;
        int queueSize = 3;
        threadPoolTaskExecutor.setCorePoolSize(minPoolSize);
        threadPoolTaskExecutor.setQueueCapacity(queueSize);
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        threadPoolTaskExecutor.setThreadFactory(new CustomThreadFactory());
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

    private static class CustomThreadFactory implements ThreadFactory {
        private final AtomicInteger threadNo = new AtomicInteger();

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("custom-thread:-" + threadNo.getAndIncrement());
            return thread;
        }
    }
}
