package com.schoolerp.mysaas.student.entity;

import com.schoolerp.mysaas.common.dto.AuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hostel_info")
public class HostelInfo extends AuditableEntity {
    private Boolean isHostelStudent;

    // Basic Info
    private String hostelName;
    private String roomNumber;

    // Optional but useful
    private String bedNumber;              // If sharing room
    private String floor;                  // For navigation

    // For internal management
    private String wardenName;             // Optional reference
    private String wardenPhone;

    // For audits
    private LocalDate hostelJoinDate;
    private LocalDate hostelLeaveDate;

    // Safety / access
    private Boolean hasKeyCard;            // If smart locks are used
    private Boolean isMealOpted;           // For mess integration

    // Emergency
    private String emergencyContactName;
    private String emergencyContactPhone;

    @Column(columnDefinition = "TEXT")
    private String specialNotes;           // Any allergy, special need, etc.
}
