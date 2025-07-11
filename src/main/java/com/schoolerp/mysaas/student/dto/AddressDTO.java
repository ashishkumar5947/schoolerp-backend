package com.schoolerp.mysaas.student.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// 📦 Address DTO
public class AddressDTO {
    private String province;
    private String district;
    private String municipality;
    private Integer wardNumber;
    private String tole;
    private String houseNumber;
    private String additionalInfo;
}
