package com.schoolerp.mysaas.common.response;

import com.schoolerp.mysaas.common.dto.AuditableEntity;
import com.schoolerp.mysaas.util.JsonMessageConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "response_messages", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"code", "tenant_code", "channel_code"})
})
public class ResponseMessage extends AuditableEntity {
    @Column(nullable = false, length = 100)
    private String code;

    @Column(nullable = false, length = 20)
    private String type; // 'ERROR' or 'SUCCESS'

    @Column(length = 100)
    private String module;

    @Column(name = "source", nullable = false, length = 20)
    private String source = "SYSTEM"; // 'SYSTEM', 'ADMIN', 'VENDOR'

    @Column(name = "messages", columnDefinition = "jsonb", nullable = false)
    @Convert(converter = JsonMessageConverter.class)
    private Map<String, String> messages;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_active")
    private Boolean isActive = true;

    private Integer version = 1;

    @Column(name = "tenant_code", nullable = false, length = 100)
    private String tenantCode;

    @Column(name = "channel_code", nullable = false, length = 50)
    private String channelCode = "ALL";
}
