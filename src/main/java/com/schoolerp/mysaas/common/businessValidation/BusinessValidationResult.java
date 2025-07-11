package com.schoolerp.mysaas.common.businessValidation;


import com.schoolerp.mysaas.common.dto.commonresponse.ErrorDetail;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BusinessValidationResult {
    private boolean valid;
    private List<ErrorDetail> errors;
}

