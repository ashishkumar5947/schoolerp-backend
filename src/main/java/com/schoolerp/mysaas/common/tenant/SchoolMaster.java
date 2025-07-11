package com.schoolerp.mysaas.common.tenant;

import com.schoolerp.mysaas.common.dto.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "school_master")
public class SchoolMaster extends AuditableEntity {
    
    @Column(name = "tenant_code", unique = true, nullable = false)
    private String tenantCode;

    @Column(name = "school_name", nullable = false)
    private String schoolName;

    @Column(name = "schema_name", nullable = false)
    private String schemaName;

    private Boolean isActive;
    private String planType;

    @Column(name = "plan_expiry_date")
    private LocalDate planExpiryDate;

    private String contactEmail;
    private String contactPhone;
    private String address;
    private String registeredBy;
    private LocalDateTime registeredOn;
}
