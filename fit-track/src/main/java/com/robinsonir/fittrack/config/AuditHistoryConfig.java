package com.robinsonir.fittrack.config;

import jakarta.persistence.EntityManager;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class AuditHistoryConfig {


    @Bean
    @Autowired
    AuditReader auditReader(EntityManager entityManager) {
        return applicationScopedAuditReader(entityManager);
    }


    /**
     * Creates an application scoped audit reader reducing chance of memory leak.
     */
    @Bean
    @RequestScope
    @Autowired
    AuditReader applicationScopedAuditReader(EntityManager entityManager) {
        return AuditReaderFactory.get(entityManager);
    }
}
