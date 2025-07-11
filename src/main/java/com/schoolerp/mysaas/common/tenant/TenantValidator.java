package com.schoolerp.mysaas.common.tenant;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TenantValidator {

    private final SchoolMasterRepository schoolMasterRepository;

    public void validateTenant(String tenantCode) {
        boolean exists = schoolMasterRepository.existsByTenantCode(tenantCode);
        if (!exists) {
            throw new IllegalArgumentException("Invalid or unregistered tenant: " + tenantCode);
        }
    }
}
