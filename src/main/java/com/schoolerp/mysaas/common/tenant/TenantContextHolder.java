package com.schoolerp.mysaas.common.tenant;

public class TenantContextHolder {
    private static final ThreadLocal<String> currentTenant = new ThreadLocal<>();

    public static void setTenantCode(String tenantCode) {
        currentTenant.set(tenantCode);
    }

    public static String getTenantCode() {
        return currentTenant.get();
    }

    public static void clear() {
        currentTenant.remove();
    }
}
