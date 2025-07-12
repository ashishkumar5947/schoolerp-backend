package com.schoolerp.mysaas.clients;

import com.schoolerp.mysaas.common.tenant.TenantSchemaResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "platformClient", url = "http://localhost:8001")
public interface PlatformClient {

    @GetMapping("/api/tenants/resolve")
    TenantSchemaResponse resolveTenant(@RequestParam("tenantCode") String tenantCode);
}
