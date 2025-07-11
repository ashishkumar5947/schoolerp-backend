package com.schoolerp.mysaas.common.fieldValidation.service;

import com.schoolerp.mysaas.common.dto.commonresponse.ErrorDetail;
import com.schoolerp.mysaas.common.fieldValidation.FieldValidationRule;
import com.schoolerp.mysaas.common.fieldValidation.FieldValidationResult;
import com.schoolerp.mysaas.common.fieldValidation.repository.FieldValidationRuleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class DynamicValidatorService {
    private static final Logger logger = LoggerFactory.getLogger(DynamicValidatorService.class);
    private final FieldValidationRuleRepository ruleRepository;

    @Autowired
    public DynamicValidatorService(FieldValidationRuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    public FieldValidationResult validate(String module, Object dto) {
        List<FieldValidationRule> rules = ruleRepository.findByModuleAndIsActiveTrue(module);
        List<ErrorDetail> errorDetails = new ArrayList<>();

        for (FieldValidationRule rule : rules) {
            try {
                Object value = getNestedFieldValue(dto, rule.getFieldName());

                // Required Check
                if (Boolean.TRUE.equals(rule.getIsRequired()) &&
                        (value == null || value.toString().trim().isEmpty())) {

                    errorDetails.add(new ErrorDetail(
                            rule.getFieldName(),
                            rule.getCustomErrorMessage() != null
                                    ? rule.getCustomErrorMessage()
                                    : "Field is required"
                    ));
                    continue;
                }

                if (value != null) {
                    String stringValue = value.toString();

                    // Regex check
                    if (rule.getRegexPattern() != null &&
                            !Pattern.matches(rule.getRegexPattern(), stringValue)) {
                        errorDetails.add(new ErrorDetail(rule.getFieldName(), "Invalid format"));
                    }

                    // Min/Max length check
                    if (rule.getMinLength() != null && stringValue.length() < rule.getMinLength()) {
                        errorDetails.add(new ErrorDetail(rule.getFieldName(), "Minimum length is " + rule.getMinLength()));
                    }
                    if (rule.getMaxLength() != null && stringValue.length() > rule.getMaxLength()) {
                        errorDetails.add(new ErrorDetail(rule.getFieldName(), "Maximum length is " + rule.getMaxLength()));
                    }

                    // Numeric range check
                    if ((rule.getMinValue() != null || rule.getMaxValue() != null)) {
                        try {
                            int intVal = Integer.parseInt(stringValue);
                            if (rule.getMinValue() != null && intVal < rule.getMinValue()) {
                                errorDetails.add(new ErrorDetail(rule.getFieldName(), "Must be >= " + rule.getMinValue()));
                            }
                            if (rule.getMaxValue() != null && intVal > rule.getMaxValue()) {
                                errorDetails.add(new ErrorDetail(rule.getFieldName(), "Must be <= " + rule.getMaxValue()));
                            }
                        } catch (NumberFormatException ignored) {
                            errorDetails.add(new ErrorDetail(rule.getFieldName(), "Value must be numeric"));
                        }
                    }
                }

            } catch (Exception e) {
                logger.warn("❌ Failed to validate field [{}]: {}", rule.getFieldName(), e.getMessage());
                errorDetails.add(new ErrorDetail(rule.getFieldName(), "Field not found or inaccessible"));
            }
        }

        return new FieldValidationResult(errorDetails.isEmpty(), errorDetails);
    }
    
    private Object getNestedFieldValue(Object dto, String fieldPath) throws Exception {
        String[] parts = fieldPath.split("\\.");
        Object currentObject = dto;

        for (String part : parts) {
            if (currentObject == null) break;

            Field field = resolveField(currentObject.getClass(), part);
            field.setAccessible(true);
            currentObject = field.get(currentObject);
        }

        return currentObject;
    }
    
    private Field resolveField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        Class<?> current = clazz;
        while (current != null) {
            try {
                return current.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                current = current.getSuperclass(); // Check parent class if not found
            }
        }
        throw new NoSuchFieldException(fieldName);
    }

}
