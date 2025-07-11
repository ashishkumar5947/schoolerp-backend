package com.schoolerp.mysaas.db;

import com.schoolerp.mysaas.common.tenant.SchemaResolver;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class DatabaseUtil {

    private final EntityManager entityManager;

    /**
     * Execute a native SQL SELECT and return a list of Object[] (rows).
     */
    public List<Object[]> fetchRows(String sql, Object... params) {
        try {
            String resolvedSql = SchemaResolver.withSchema(sql);
            log.info("📥 Executing fetchRows | SQL: {}", resolvedSql);
            log.info("🔢 Parameters: {}", Arrays.toString(params));

            Query query = entityManager.createNativeQuery(resolvedSql);
            setParameters(query, params);
            return query.getResultList();

        } catch (Exception e) {
            log.error("❌ Error executing native fetchRows: {}", sql, e);
            return List.of();
        }
    }

    /**
     * Fetch a single row.
     */
    public Optional<Object[]> selectAsObject(String sql, Object... params) {
        try {
            String resolvedSql = SchemaResolver.withSchema(sql);
            log.info("📥 Executing selectAsObject | SQL: {}", resolvedSql);
            log.info("🔢 Parameters: {}", Arrays.toString(params));

            Query query = entityManager.createNativeQuery(resolvedSql);
            setParameters(query, params);
            return Optional.of((Object[]) query.getSingleResult());

        } catch (Exception e) {
            log.warn("⚠️ No result or error in selectAsObject: {}", sql, e);
            return Optional.empty();
        }
    }

    /**
     * Save any JPA Entity (must be schema-bound by EntityManager's context)
     */
    @Transactional
    public void save(String tenantCode, String channelCode, Object entity) {
        try {
            entityManager.persist(entity);
            entityManager.flush();
            log.info("✅ Entity persisted: {} | Tenant: {}, Channel: {}", entity.getClass().getSimpleName(), tenantCode, channelCode);
        } catch (Exception e) {
            log.error("❌ Error persisting entity: {}", entity, e);
            throw e;
        }
    }

    /**
     * Paginated query execution.
     */
    public List<Object[]> fetchRowsWithPagination(String sql, int page, int size, Object... params) {
        try {
            String resolvedSql = SchemaResolver.withSchema(sql);
            String paginatedSql = resolvedSql + " LIMIT ? OFFSET ?";

            log.info("🟡 Executing paginated query: {}", resolvedSql);
            log.info("📄 Page: {}, Size: {}, OFFSET: {}, LIMIT: {}", page, size, (page * size), size);
            log.info("🔢 Parameters: {}", Arrays.toString(params));

            Query query = entityManager.createNativeQuery(paginatedSql);

            // Bind filters
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i + 1, params[i]);
                log.info("🔹 Binding param {} = {}", i + 1, params[i]);
            }

            // Pagination params
            query.setParameter(params.length + 1, size);         // LIMIT
            query.setParameter(params.length + 2, page * size);  // OFFSET

            return query.getResultList();
        } catch (Exception e) {
            log.error("❌ Error in fetchRowsWithPagination: {}", sql, e);
            throw new RuntimeException("Pagination query failed");
        }
    }

    /**
     * Count query for paginated lists.
     */
    public Long fetchCount(String sql, Object... params) {
        try {
            String resolvedSql = SchemaResolver.withSchema(sql);
            log.info("🧮 Executing count query: {}", resolvedSql);
            log.info("🔢 Parameters: {}", Arrays.toString(params));

            Query query = entityManager.createNativeQuery(resolvedSql);
            setParameters(query, params);

            Number count = (Number) query.getSingleResult();
            log.info("🔢 Count result = {}", count.longValue());
            return count.longValue();

        } catch (Exception e) {
            log.error("❌ Error executing count query: {}", sql, e);
            throw new RuntimeException("Count query failed");
        }
    }

    /**
     * Utility to bind parameters to Query object.
     */
    private void setParameters(Query query, Object... params) {
        for (int i = 0; i < params.length; i++) {
            query.setParameter(i + 1, params[i]);
            log.debug("🔹 Binding param {} = {}", i + 1, params[i]);
        }
    }
}
