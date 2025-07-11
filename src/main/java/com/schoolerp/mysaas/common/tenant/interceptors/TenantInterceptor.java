package com.schoolerp.mysaas.common.tenant.interceptors;

import com.schoolerp.mysaas.common.tenant.TenantContextHolder;
import com.schoolerp.mysaas.common.tenant.TenantValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TenantInterceptor implements HandlerInterceptor {

    private final TenantValidator tenantValidator;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String tenantCode = request.getHeader("X-Tenant-Code");

        // Step 1: Validate presence
        if (tenantCode == null || tenantCode.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Missing X-Tenant-Code header");
            return false;
        }

        // Step 2: Validate tenant exists
        try {
            tenantValidator.validateTenant(tenantCode);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(e.getMessage());
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Step 3: Set in context
        TenantContextHolder.setTenantCode(tenantCode);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        TenantContextHolder.clear();
    }
}
