package com.schoolerp.mysaas.student.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// 🎯 Student Create Request
public class StudentCreateRequest {

    // 🏫 Academic Info
    private String firstName;
    private String lastName;
    private String className;
    private String section;
    private Integer rollNumber;
    private LocalDate admissionDate;
    private String previousSchoolName;

    // 👤 Personal Info
    private PersonalInfoDTO personalInfo;

    // 📞 Contact Info
    private ContactInfoDTO contactInfo;

    // 👪 Guardian Info
    private GuardianInfoDTO guardianInfo;

    // 🏠 Address Info
    private AddressDTO address;

    // 🛏 Hostel Info
    private HostelInfoDTO hostelInfo;

    // 🛏 Hostel Info
    private List<DocumentInfoDTO> documentInfoDTO;

    // 🔐 App Integration
    private String parentPortalUserId;
    private String firebaseUid;

    // 📌 Status
    private Boolean isActive;
    private LocalDate exitDate;
    private String remarks;
}

