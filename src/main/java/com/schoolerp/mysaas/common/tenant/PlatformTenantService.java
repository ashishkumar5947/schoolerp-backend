package com.schoolerp.mysaas.common.tenant;

import com.schoolerp.mysaas.clients.PlatformClient;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PlatformTenantService {

    private final PlatformClient platformClient;
    private final Map<String, CachedTenantInfo> cache = new ConcurrentHashMap<>();

    public PlatformTenantService(@Lazy PlatformClient platformClient) {
        this.platformClient = platformClient;
    }

    public CachedTenantInfo getTenantInfo(String tenantCode) {
        return cache.computeIfAbsent(tenantCode, code -> {
            TenantSchemaResponse res = platformClient.resolveTenant(code);
            return new CachedTenantInfo(res.getSchema(), res.getPlanExpiryDate(), res.getIsActive());
        });
    }

    public void evict(String tenantCode) {
        cache.remove(tenantCode);
    }
}