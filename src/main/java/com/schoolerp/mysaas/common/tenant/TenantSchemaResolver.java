package com.schoolerp.mysaas.common.tenant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TenantSchemaResolver {
    
    private final PlatformTenantService platformTenantService;

    @Autowired
    public TenantSchemaResolver(PlatformTenantService platformTenantService) {
        this.platformTenantService = platformTenantService;
    }

    public String resolveSchema(String tenantCode) {
        CachedTenantInfo info = platformTenantService.getTenantInfo(tenantCode);

        if (info == null) {
            throw new RuntimeException("❌ Tenant not found: " + tenantCode);
        }
        if (!Boolean.TRUE.equals(info.getIsActive())) {
            throw new RuntimeException("❌ Tenant is inactive: " + tenantCode);
        }

        return info.getSchema();
    }
}
