package com.schoolerp.mysaas.common.businessValidation.rules;

import com.schoolerp.mysaas.common.businessValidation.AbstractBusinessRule;
import com.schoolerp.mysaas.common.dto.commonresponse.ErrorDetail;
import com.schoolerp.mysaas.common.emum.AllEnum;
import com.schoolerp.mysaas.student.dto.StudentCreateRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
public class GenderValidationRule extends AbstractBusinessRule<Object> {

    @Override
    public Optional<ErrorDetail> validate(Object dto) {
        if (!(dto instanceof StudentCreateRequest request)) {
            return Optional.empty(); // Skip if not the expected type
        }

        String gender = request.getPersonalInfo().getGender();
        if (gender != null && Arrays.stream(AllEnum.Gender.values())
                .noneMatch(e -> e.name().equalsIgnoreCase(gender))) {
            return Optional.of(new ErrorDetail("personalInfo.gender", "Invalid gender"));
        }

        return Optional.empty();
    }

    @Override
    public String getField() {
        return "personalInfo.gender";
    }

    @Override
    public String getModule() {
        return "STUDENT";
    }
}
