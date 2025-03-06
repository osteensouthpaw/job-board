package com.omega.jobportal.searching;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Configuration
public class IndexingService implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger log = LoggerFactory.getLogger(IndexingService.class);
    private final EntityManager entityManager;

    @Override
    @Transactional
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        SearchSession session = Search.session(entityManager);
        try {
            session.massIndexer()
                    .idFetchSize(80)
                    .batchSizeToLoadObjects(15)
                    .threadsToLoadObjects(Runtime.getRuntime().availableProcessors())
                    .startAndWait();
            log.info("Mass indexing successful");
        } catch (InterruptedException e) {
            log.error("Mass indexing failed {}", e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
