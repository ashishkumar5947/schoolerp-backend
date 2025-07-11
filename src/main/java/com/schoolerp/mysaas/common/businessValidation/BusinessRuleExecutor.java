package com.schoolerp.mysaas.common.businessValidation;

import com.schoolerp.mysaas.common.dto.commonresponse.ErrorDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BusinessRuleExecutor {

    private final List<BusinessRule<?>> allRules;

    @Autowired
    public BusinessRuleExecutor(List<BusinessRule<?>> allRules) {
        this.allRules = allRules;
    }

    public <T> BusinessValidationResult validate(String module, T dto) {
        List<ErrorDetail> errorDetails = allRules.stream()
                .filter(rule -> rule.getModule().equalsIgnoreCase(module))
                .map(rule -> (BusinessRule<T>) rule) // unchecked but safe
                .filter(rule -> rule.isApplicable(dto))
                .map(rule -> rule.validate(dto))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        return new BusinessValidationResult(errorDetails.isEmpty(), errorDetails);
    }
}
