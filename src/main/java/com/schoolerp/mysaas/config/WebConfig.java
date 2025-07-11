package com.schoolerp.mysaas.config;

import com.schoolerp.mysaas.common.tenant.interceptors.PlanExpiryInterceptor;
import com.schoolerp.mysaas.common.tenant.interceptors.TenantInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final TenantInterceptor tenantInterceptor;
    private final PlanExpiryInterceptor planExpiryInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tenantInterceptor)
                .addPathPatterns("/**")
                .order(1);  // First: validate tenant and set schema

        registry.addInterceptor(planExpiryInterceptor)
                .addPathPatterns("/**")
                .order(2);  // Second: check for plan expiry
    }
}
