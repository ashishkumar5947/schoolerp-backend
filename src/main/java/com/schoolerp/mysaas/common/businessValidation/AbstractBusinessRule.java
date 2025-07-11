package com.schoolerp.mysaas.common.businessValidation;

public abstract class AbstractBusinessRule<T> implements BusinessRule<T> {
    @Override
    public boolean isApplicable(T dto) {
        return true; // Default always applicable
    }
}
