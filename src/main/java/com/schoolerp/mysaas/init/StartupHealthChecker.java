package com.schoolerp.mysaas.init;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StartupHealthChecker {

    private final EntityManager entityManager;

    @PostConstruct
    public void checkDatabase() {
        log.info("🔍 Running database health check...");

        try {
            Query query = entityManager.createNativeQuery("SELECT 1");
            query.getSingleResult();
            log.info("✅ Database is up and running!");
        } catch (Exception e) {
            log.error("❌ Database connection failed: {}", e.getMessage());
        }
    }
}
