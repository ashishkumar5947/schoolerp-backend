package com.schoolerp.mysaas.common.tenant;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TenantSchemaResponse {

    private String schema;             // ← schema name
    private Boolean isActive;          // ← active/inactive
    private LocalDate planExpiryDate;  // ← for license check
}