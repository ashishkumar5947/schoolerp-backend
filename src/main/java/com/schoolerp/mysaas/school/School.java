package com.schoolerp.mysaas.school;

import com.schoolerp.mysaas.common.dto.AuditableEntity;
import jakarta.persistence.*;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Entity
@Table(name = "schools")
public class School extends AuditableEntity {
    private String name;
    private String code;

    private String affiliationNumber;
    private String registrationNumber;

    private String address;
    private String city;
    private String state;
    private String country;
    private String pinCode;
    private Boolean multipleCampuses = false;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_school_id")
    private School parentSchool;

    private String phone;
    private String email;
    private String website;
    private String principalName;
    private String principalEmail;
    private String principalPhone;

    private String logoUrl;
    private String themeColor = "#0052cc";
    private String domainSubdomain;

    private Integer academicYearStartMonth = 1;
    private String gradingSystem = "percentage";
    private String currency = "NPR";

    private String licenseType = "FREE";
    private Integer studentLimit = 50;
    private String defaultLanguage = "en";
    private LocalDate licenseStartDate;
    private LocalDate licenseEndDate;
    private Boolean isActive = true;

    private Boolean hasHostel = false;
    private String emergencyContactName;
    private String emergencyContactPhone;

    private Boolean enableLibrary = true;
    private Boolean enableTransport = false;
    private Boolean enableExams = true;
    private Boolean enableAttendance = true;

    private String remarks;
}
