package com.schoolerp.mysaas.student.entity;

import com.schoolerp.mysaas.common.dto.AuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class DocumentInfo extends AuditableEntity {
    private String photoUrl;

    // Identity Documents
    private String idCardNumber;                  // School-issued ID
    private String birthCertificateUrl;
    private String citizenshipCertificateUrl;     // For Nepali students (16+)
    private String passportUrl;                   // Optional for foreign trips

    // Academic Documents
    private String previousSchoolTcUrl;           // Transfer Certificate
    private String previousMarksheetUrl;          // Last year's result
    private String characterCertificateUrl;

    // Health & Safety
    private String vaccinationCardUrl;
    private String medicalCertificateUrl;

    // Parent/Guardian Docs
    private String parentIdProofUrl;              // National ID or Citizenship proof
    private String guardianSignatureUrl;          // For offline forms

    // Optional
    private String casteCertificateUrl;           // If category-based quota
    private String incomeCertificateUrl;          // For scholarship eligibility

    @Column(columnDefinition = "TEXT")
    private String additionalNotes;               // Free-form document notes

}
