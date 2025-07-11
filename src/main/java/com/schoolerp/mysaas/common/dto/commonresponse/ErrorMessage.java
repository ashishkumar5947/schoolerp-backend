package com.schoolerp.mysaas.common.dto.commonresponse;

import com.schoolerp.mysaas.common.dto.AuditableEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "error_messages")
public class ErrorMessage extends AuditableEntity {
    
    private String module;
    private int code;

    private String messageEn;
    private String messageHi;
    private String messageNp;

    private int httpStatus;
    private boolean isClientVisible;
    private String developerNote;
}
