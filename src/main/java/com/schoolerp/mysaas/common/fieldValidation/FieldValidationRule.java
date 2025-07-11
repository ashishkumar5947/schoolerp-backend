package com.schoolerp.mysaas.common.fieldValidation;

import com.schoolerp.mysaas.common.dto.AuditableEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "field_validation_rules")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldValidationRule extends AuditableEntity {
    
    private String module;       // e.g., "Student"
    private String fieldName;        // e.g., "email"
    private String fieldType;        // e.g., "String", "Integer"
    private Boolean isRequired;
    private Integer minLength;
    private Integer maxLength;
    private String regexPattern;
    private Integer minValue;
    private Integer maxValue;
    private String customErrorMessage;
    private Boolean isActive = true;
}
