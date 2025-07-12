package com.schoolerp.mysaas.common.tenant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TenantCache {

    private final Map<String, CachedTenantInfo> cache = new ConcurrentHashMap<>();

    public CachedTenantInfo getCachedInfo(String tenantCode) {
        return cache.get(tenantCode);
    }

    public void put(String tenantCode, CachedTenantInfo info) {
        cache.put(tenantCode, info);
    }

    public void evict(String tenantCode) {
        cache.remove(tenantCode);
    }
}
