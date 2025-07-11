package com.schoolerp.mysaas.student.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentFilterRequest {
    private String className;
    private String section;
    private Integer rollNumber;
}
