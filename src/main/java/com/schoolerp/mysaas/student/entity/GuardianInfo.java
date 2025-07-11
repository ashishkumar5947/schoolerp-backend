package com.schoolerp.mysaas.student.entity;

import com.schoolerp.mysaas.common.dto.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "guardian_info")
public class GuardianInfo extends AuditableEntity {
    private String guardianName;
    private String guardianRelation;
    private String guardianEmail;
    private String guardianPhone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_info_id")
    private ContactInfo contactInfo;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_info_id")
    private AddressInfo addressInfo;
}
