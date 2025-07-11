package com.schoolerp.mysaas.student.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentCreateResponse {
    private UUID studentId;
    private String admissionNo;
    private String firstName;
    private String lastName;
    private String className;
    private String section;
    private Integer rollNumber;
    private LocalDate admissionDate;
    private String previousSchoolName;

    private PersonalInfoDTO personalInfo;
    private ContactInfoDTO contactInfo;
    private GuardianInfoDTO guardianInfo;
    private AddressDTO address;
    private HostelInfoDTO hostelInfo;

    private String parentPortalUserId;
    private String firebaseUid;
    private Boolean isActive;
    private LocalDate exitDate;
    private String remarks;

    private Long schoolId;
    private String createdBy;
    private String createdAt;

    private String message;
}
