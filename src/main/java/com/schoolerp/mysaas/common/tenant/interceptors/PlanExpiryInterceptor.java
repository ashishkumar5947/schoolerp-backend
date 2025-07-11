package com.schoolerp.mysaas.common.tenant.interceptors;

import com.schoolerp.mysaas.common.tenant.SchoolMaster;
import com.schoolerp.mysaas.common.tenant.SchoolMasterRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PlanExpiryInterceptor implements HandlerInterceptor {

    private final SchoolMasterRepository schoolMasterRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String tenantCode = request.getHeader("X-Tenant-Code");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (tenantCode == null || tenantCode.isBlank()) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write("{\"status\":400,\"message\":\"Missing X-Tenant-Code header\"}");
            return false;
        }

        Optional<SchoolMaster> optionalSchool = schoolMasterRepository.findByTenantCode(tenantCode);
        if (optionalSchool.isEmpty()) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("{\"status\":401,\"message\":\"Invalid tenant code\"}");
            return false;
        }

        SchoolMaster school = optionalSchool.get();
        if (school.getPlanExpiryDate() != null && school.getPlanExpiryDate().isBefore(LocalDate.now())) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write("{\"status\":403,\"message\":\"School plan has expired\"}");
            return false;
        }

        return true;
    }

}