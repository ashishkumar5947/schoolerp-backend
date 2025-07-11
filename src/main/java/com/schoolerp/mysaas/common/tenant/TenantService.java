package com.schoolerp.mysaas.common.tenant;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TenantService {

    private final SchoolMasterRepository schoolMasterRepository;

    public String resolveSchema(String tenantCode) throws Exception {
        SchoolMaster school = schoolMasterRepository.findByTenantCode(tenantCode)
                .orElseThrow(() -> new RuntimeException("⛔ Invalid tenant: " + tenantCode));

        if (!Boolean.TRUE.equals(school.getIsActive())) {
            throw new RuntimeException("⛔ School is inactive or plan expired");
        }

        return school.getSchemaName(); // e.g. platform_ramscl
    }
}
