package com.schoolerp.mysaas.student.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.schoolerp.mysaas.common.dto.AuditableEntity;
import com.schoolerp.mysaas.student.emum.StudentEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@ToString(exclude = "student")
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "document_info")
public class Document extends AuditableEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 50)
    private StudentEnum.DocumentType type;

    @Column(name = "file_url", nullable = false, columnDefinition = "TEXT")
    private String fileUrl;

    @Column(name = "uploaded_at", nullable = false)
    private LocalDate uploadedAt;

    @Column(name = "uploaded_by", nullable = false)
    private String uploadedBy;

    // 🔗 Foreign key to student
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    @JsonManagedReference
    private Student student;
}
