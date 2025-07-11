package com.schoolerp.mysaas.common.dto.commonresponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> {

    private boolean validationSuccess;
    private boolean processingSuccess;        // true/false
    private String message;                   // Success/Error message
    private Integer statusCode;               // 200, 404, 500...
    private T data;                           // Actual result
    private List<ErrorDetail> errors;         // For validation/business errors
    private PaginationMeta pagination;        // If it's a paged response
    private String path;                      // Requested URI
    private LocalDateTime timestamp;          // When response was created
    private Map<String, Object> debugInfo;    // Optional: stacktrace, requestId etc.
}
