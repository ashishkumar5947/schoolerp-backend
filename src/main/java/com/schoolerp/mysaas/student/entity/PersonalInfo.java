package com.schoolerp.mysaas.student.entity;

import com.schoolerp.mysaas.common.dto.AuditableEntity;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PersonalInfo extends AuditableEntity {
    private LocalDate dob;
    private String gender;
    private String bloodGroup;
    private String nationality;            // e.g., Nepali

    private String religion;

    // 🇳🇵 Identity Info
    private String birthCertificateNumber; // Used in school mostly
    private String citizenshipNumber;      // For 16+ age
    private String passportNumber;         // Optional
}

