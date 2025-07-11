package com.schoolerp.mysaas.student.controller;

import com.schoolerp.mysaas.common.businessValidation.BusinessRuleExecutor;
import com.schoolerp.mysaas.common.businessValidation.BusinessValidationResult;
import com.schoolerp.mysaas.common.dto.commonresponse.CommonResponse;
import com.schoolerp.mysaas.common.dto.commonresponse.PaginationMeta;
import com.schoolerp.mysaas.common.emum.AllEnum;
import com.schoolerp.mysaas.common.fieldValidation.FieldValidationResult;
import com.schoolerp.mysaas.common.fieldValidation.service.DynamicValidatorService;
import com.schoolerp.mysaas.student.dto.StudentCreateRequest;
import com.schoolerp.mysaas.student.dto.StudentCreateResponse;
import com.schoolerp.mysaas.student.dto.StudentListRequest;
import com.schoolerp.mysaas.student.dto.StudentListResponse;
import com.schoolerp.mysaas.student.service.StudentService;
import com.schoolerp.mysaas.util.CommonResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/student")
public class StudentController {
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
    
    private final String module = AllEnum.Module.STUDENT.name();
    private final CommonResponseUtil commonResponseUtil;
    private final DynamicValidatorService validatorService;
    private final BusinessRuleExecutor businessRuleExecutor;
    private final StudentService studentService;

    public StudentController(CommonResponseUtil commonResponseUtil, DynamicValidatorService validatorService, BusinessRuleExecutor businessRuleExecutor, StudentService studentService) {
        this.commonResponseUtil = commonResponseUtil;
        this.validatorService = validatorService;
        this.businessRuleExecutor = businessRuleExecutor;
        this.studentService = studentService;
    }

    @PostMapping("/create")
    public CommonResponse<StudentCreateResponse> createStudent(@RequestBody StudentCreateRequest studentCreateRequest,
                                                               @RequestHeader("X-Tenant-Code") String tenantCode,
                                                               @RequestHeader(value = "Accept-Language", defaultValue = "en-US") String locale,
                                                               @RequestHeader(value = "X-Channel-Code", defaultValue = "ALL") String channelCode ) {
        CommonResponse<StudentCreateResponse> commonResponse = new CommonResponse<>();
        
        // FIELD VALIDATIONS    
        FieldValidationResult fieldValidationResult = validatorService.validate(module, studentCreateRequest);
        if (!fieldValidationResult.isValid()) {
            return commonResponseUtil.fieldValidationError(fieldValidationResult);
        }

        // BUSINESS VALIDATIONS
        BusinessValidationResult businessValidationResult = businessRuleExecutor.validate(module, studentCreateRequest);
        if (!businessValidationResult.isValid()) {
            return commonResponseUtil.businessValidationError(businessValidationResult);
        }
        
        // REGISTER STUDENT
        try {
            commonResponse = studentService.createStudent(tenantCode, channelCode, locale, studentCreateRequest);
        } catch (Exception e) {
            commonResponse = commonResponseUtil.exceptionError(e);
        }
        
        return commonResponse;
    }

    // 📄 LIST STUDENTS WITH FILTERS + PAGINATION
    @PostMapping("/list")
    public CommonResponse<List<StudentListResponse>> listStudents(@RequestBody StudentListRequest request,
                                                                  @RequestHeader("X-Tenant-Code") String tenantCode,
                                                                  @RequestHeader(value = "Accept-Language", defaultValue = "en-US") String locale,
                                                                  @RequestHeader(value = "X-Channel-Code", defaultValue = "ALL") String channelCode) {
        logger.info("📥 [listStudents] Request received | tenant: {}, channel: {}, request: {}", tenantCode, channelCode, request);

        try {
            return studentService.listStudents(tenantCode, channelCode, locale, request);
        } catch (Exception ex) {
            logger.error("❌ [listStudents] Error occurred while listing students: {}", ex.getMessage(), ex);

            return CommonResponse.<List<StudentListResponse>>builder()
                    .validationSuccess(true)
                    .processingSuccess(false)
                    .statusCode(500)
                    .message("Failed to fetch student list")
                    .data(List.of())
                    .pagination(PaginationMeta.builder().page(0).size(0).totalElements(0).totalPages(0).lastPage(true).build())
                    .timestamp(java.time.LocalDateTime.now())
                    .build();
        }
    }

}
