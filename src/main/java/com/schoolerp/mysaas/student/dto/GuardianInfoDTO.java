package com.schoolerp.mysaas.student.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// 📦 Guardian Info DTO
public class GuardianInfoDTO {
    private String guardianName;
    private String guardianRelation;
    private String guardianEmail;
    private String guardianPhone;
    private String emergencyContactName;
    private String emergencyContactPhone;
}
