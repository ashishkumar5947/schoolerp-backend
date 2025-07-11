package com.schoolerp.mysaas.common.businessValidation;

import com.schoolerp.mysaas.common.dto.commonresponse.ErrorDetail;

import java.util.Optional;

public interface BusinessRule<T> {
    boolean isApplicable(T dto);
    Optional<ErrorDetail> validate(T dto);
    String getField();
    String getModule();
}
