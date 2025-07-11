package com.schoolerp.mysaas.student.entity;

import com.schoolerp.mysaas.common.dto.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contact_info")
public class ContactInfo extends AuditableEntity {
    private String guardianId;
    
    private String email;
    private String phone;

    @OneToOne(cascade = CascadeType.ALL)
    private AddressInfo addressInfo;
}
