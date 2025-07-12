package com.schoolerp.mysaas.common.tenant;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CachedTenantInfo {
    private String schema;
    private LocalDate expiryDate;
    private Boolean isActive;
}
