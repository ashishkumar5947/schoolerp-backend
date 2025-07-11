package com.schoolerp.mysaas.student.dto;

import com.schoolerp.mysaas.student.emum.StudentEnum;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentInfoDTO {
    private StudentEnum.DocumentType type;
    private String fileUrl;
    private LocalDate uploadedAt;
    private String uploadedBy;
}