package com.schoolerp.mysaas.student.entity;

import com.schoolerp.mysaas.common.dto.AuditableEntity;
import jakarta.persistence.Entity;
import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address_info")
public class AddressInfo extends AuditableEntity {
    private String guardianId;
    private String province;           // e.g. Bagmati
    private String district;           // e.g. Kathmandu
    private String municipality;       // e.g. Kathmandu Metropolitan
    private Integer wardNumber;        // e.g. 5
    private String tole;               // e.g. Putalisadak, Chabahil
    private String houseNumber;        // optional
    private String additionalInfo;     // optional description

}
