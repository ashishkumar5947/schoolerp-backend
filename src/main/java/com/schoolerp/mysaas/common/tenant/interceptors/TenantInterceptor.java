package com.schoolerp.mysaas.common.tenant.interceptors;

import com.schoolerp.mysaas.common.tenant.CachedTenantInfo;
import com.schoolerp.mysaas.common.tenant.PlatformTenantService;
import com.schoolerp.mysaas.common.tenant.TenantContextHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class TenantInterceptor implements HandlerInterceptor {

    private final PlatformTenantService platformTenantService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String tenantCode = request.getHeader("X-Tenant-Code");

        // 1. Validate presence of header
        if (tenantCode == null || tenantCode.trim().isEmpty()) {
            log.warn("🚫 Missing X-Tenant-Code header");
            response.sendError(HttpStatus.BAD_REQUEST.value(), "Missing tenant header");
            return false;
        }

        // 2. Check cache for schema + license info
        CachedTenantInfo info = platformTenantService.getTenantInfo(tenantCode);
        if (info == null) {
            log.warn("❌ Tenant not found in cache: {}", tenantCode);
            response.sendError(HttpStatus.NOT_FOUND.value(), "Tenant not found");
            return false;
        }

        // 3. Check active & expiry
        if (!info.getIsActive()) {
            log.warn("🔒 Inactive tenant: {}", tenantCode);
            response.sendError(HttpStatus.FORBIDDEN.value(), "Tenant is inactive");
            return false;
        }

        if (info.getExpiryDate() != null && info.getExpiryDate().isBefore(LocalDate.now())) {
            log.warn("📅 License expired for tenant: {}", tenantCode);
            response.sendError(HttpStatus.FORBIDDEN.value(), "Tenant license expired");
            return false;
        }
        
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        TenantContextHolder.clear();
    }
}
