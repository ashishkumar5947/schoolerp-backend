package com.schoolerp.mysaas.student.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// 📦 Hostel Info DTO
public class HostelInfoDTO {
    private Boolean isHostelStudent;
    private String hostelName;
    private String roomNumber;
    private String bedNumber;
    private String floor;
    private String wardenName;
    private String wardenPhone;
    private LocalDate hostelJoinDate;
    private LocalDate hostelLeaveDate;
    private Boolean hasKeyCard;
    private Boolean isMealOpted;
    // Emergency
    private String emergencyContactName;
    private String emergencyContactPhone;
    private String specialNotes;
}
