package com.schoolerp.mysaas.student.dto;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// 📦 Personal Info DTO
public class PersonalInfoDTO {
    private LocalDate dob;
    private String gender;
    private String bloodGroup;
    private String nationality;
    private String religion;
    private String birthCertificateNumber;
    private String citizenshipNumber;
    private String passportNumber;
}
