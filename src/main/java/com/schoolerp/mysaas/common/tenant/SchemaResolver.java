package com.schoolerp.mysaas.common.tenant;

public class SchemaResolver {

    public static String resolveSchema() {
        String tenantCode = TenantContextHolder.getTenantCode();
        if (tenantCode == null || tenantCode.isBlank()) {
            throw new IllegalStateException("Tenant code not set in context");
        }
        return "platform_" + tenantCode.toLowerCase(); // customize prefix if needed
    }

    public static String withSchema(String sql) {
        String schema = resolveSchema();
        return sql.replaceAll("\\bFROM\\s+(\\w+)", "FROM " + schema + ".$1")
                .replaceAll("\\bJOIN\\s+(\\w+)", "JOIN " + schema + ".$1");
    }
}
