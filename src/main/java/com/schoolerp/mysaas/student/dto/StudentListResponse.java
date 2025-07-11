package com.schoolerp.mysaas.student.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentListResponse {
    private UUID id;
    private String admissionNo;
    private String firstName;
    private String lastName;
    private String className;
    private String section;
    private Integer rollNumber;
}

