package com.schoolerp.mysaas.common.fieldValidation;

import com.schoolerp.mysaas.common.dto.commonresponse.ErrorDetail;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FieldValidationResult {
    private boolean valid;
    private List<ErrorDetail> errors;
}
