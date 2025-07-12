package com.schoolerp.mysaas.common.tenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver<String> {

    private final TenantSchemaResolver resolver;
    private final String defaultSchema;

    public TenantIdentifierResolver(TenantSchemaResolver resolver, @Value("${schoolerp.default-schema}") String defaultSchema) {
        this.resolver = resolver;
        this.defaultSchema = defaultSchema;
    }

    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenantCode = TenantContextHolder.getTenantCode();

        if (tenantCode == null) {
            return defaultSchema; // ✅ dynamic, no hardcoding
        }

        return resolver.resolveSchema(tenantCode); // 🔥 resolve from DB
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
