package com.schoolerp.mysaas.common.tenant;

import com.schoolerp.mysaas.clients.PlatformClient;
import org.springframework.stereotype.Service;

@Service
public class PlatformResolverService {

    private final PlatformClient platformClient;

    public PlatformResolverService(PlatformClient platformClient) {
        this.platformClient = platformClient;
    }

    public TenantSchemaResponse resolveTenant(String tenantCode) {
        return platformClient.resolveTenant(tenantCode);
    }
}
