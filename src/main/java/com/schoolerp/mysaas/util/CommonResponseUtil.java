package com.schoolerp.mysaas.util;

import com.schoolerp.mysaas.common.businessValidation.BusinessValidationResult;
import com.schoolerp.mysaas.common.dto.commonresponse.CommonResponse;
import com.schoolerp.mysaas.common.dto.commonresponse.ErrorDetail;
import com.schoolerp.mysaas.common.dto.commonresponse.PaginationMeta;
import com.schoolerp.mysaas.common.fieldValidation.FieldValidationResult;
import com.schoolerp.mysaas.common.response.ResponseMessageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CommonResponseUtil {

    private final ResponseMessageService messageService;

    public <T> CommonResponse<T> success(String tenantCode, String locale, String module, int code, T data) {
        return CommonResponse.<T>builder()
                .validationSuccess(false)
                .processingSuccess(false)
                .message(getMessage(tenantCode, locale, module, code))
                .statusCode(HttpStatus.OK.value())
                .data(data)
                .path("")
                .timestamp(LocalDateTime.now())
                .build();
    }

    public <T> CommonResponse<T> paginated(String tenantCode, String locale, String module, int code, T data, PaginationMeta meta) {
        return CommonResponse.<T>builder()
                .validationSuccess(false)
                .processingSuccess(false)
                .message(getMessage(tenantCode, locale, module, code))
                .statusCode(HttpStatus.OK.value())
                .data(data)
                .pagination(meta)
                .path("")
                .timestamp(LocalDateTime.now())
                .build();
    }

    public CommonResponse<?> error(String tenantCode, String locale, String module, int code, HttpStatus status, List<ErrorDetail> errors, HttpServletRequest request) {
        return CommonResponse.builder()
                .validationSuccess(false)
                .processingSuccess(false)
                .message(getMessage(tenantCode, locale, module, code))
                .statusCode(status.value())
                .errors(errors)
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
    }

    public <T> CommonResponse<T> exceptionError(Exception e) {
        return CommonResponse.<T>builder()
                .validationSuccess(false)
                .processingSuccess(false)
                .message(e.getMessage() != null ? e.getMessage() : "Unexpected error occurred")
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .path("")
                .timestamp(LocalDateTime.now())
                .build();
    }

    public <T> CommonResponse<T> fieldValidationError(FieldValidationResult fieldValidationResult) {
        return CommonResponse.<T>builder()
                .validationSuccess(false)
                .processingSuccess(false)
                .statusCode(400)
                .message("Validation failed")
                .errors(fieldValidationResult.getErrors())
                .path("")
                .timestamp(LocalDateTime.now())
                .build();
    }

    public <T> CommonResponse<T> businessValidationError(BusinessValidationResult businessValidationResult) {
        return CommonResponse.<T>builder()
                .validationSuccess(false)
                .processingSuccess(false)
                .statusCode(400)
                .message("Validation failed")
                .errors(businessValidationResult.getErrors())
                .path("")
                .timestamp(LocalDateTime.now())
                .build();
    }
    
    private String getMessage(String tenantCode, String locale, String module, int code) {
        return messageService.getMessage(tenantCode, locale, code);
    }
}
