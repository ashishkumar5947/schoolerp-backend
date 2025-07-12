package com.schoolerp.mysaas.student.service;

import com.schoolerp.mysaas.common.dto.commonresponse.CommonResponse;
import com.schoolerp.mysaas.common.dto.commonresponse.PaginationMeta;
import com.schoolerp.mysaas.common.emum.AllEnum;
import com.schoolerp.mysaas.common.emum.MessageCodeEnum;
import com.schoolerp.mysaas.common.filterRequest.PaginationRequest;
import com.schoolerp.mysaas.common.filterRequest.SearchRequest;
import com.schoolerp.mysaas.student.dto.*;
import com.schoolerp.mysaas.student.entity.Student;
import com.schoolerp.mysaas.student.repository.StudentRepository;
import com.schoolerp.mysaas.student.util.StudentMapper;
import com.schoolerp.mysaas.student.util.StudentNativeMapper;
import com.schoolerp.mysaas.util.CommonResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class StudentService {
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final CommonResponseUtil commonResponseUtil;
    private final StudentMapper studentMapper;
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(CommonResponseUtil commonResponseUtil, StudentMapper studentMapper, StudentRepository studentRepository) {
        this.commonResponseUtil = commonResponseUtil;
        this.studentMapper = studentMapper;
        this.studentRepository = studentRepository;
    }

    public CommonResponse<StudentCreateResponse> createStudent(String tenantCode, String channelCode, String locale, StudentCreateRequest studentCreateRequest) {
        logger.info("📥 [createStudent] Request received for tenant={}, channel={}, locale={}", tenantCode, channelCode, locale);

        String module = AllEnum.Module.STUDENT.name();
        CommonResponse<StudentCreateResponse> commonResponse = new CommonResponse<>();
        
        try {
            // 🏗 1. Map Request to Entity
            Student student = studentMapper.toEntity(studentCreateRequest);

            // 💾 4. Save to DB
            Random random = new Random();
            long number = 1_000_000_000L + (long)(random.nextDouble() * 9_000_000_000L); // Ensures 10-digit
            String admissionNo = "TEST" + number;
            student.setAdmissionNo(admissionNo);
            student.setSchoolId(10L + (long)(random.nextDouble() * 9000L));
            studentRepository.save(student);

            // 📤 5. Map Entity to Response DTO
            StudentCreateResponse studentResponse = studentMapper.toResponse(student);
            commonResponse = commonResponseUtil.success(tenantCode, locale, module, MessageCodeEnum.STUDENT_MC.MC_100.getCode(), studentResponse);

            logger.info("✅ Student created: ID={}, Name={} {}", student.getId(), student.getFirstName(), student.getLastName());

        } catch (Exception e) {
            commonResponse = commonResponseUtil.exceptionError(e);
        }
       
        return commonResponse;
    }

    public CommonResponse<List<StudentListResponse>> listStudents(
            String tenantCode,
            String channelCode,
            String locale,
            StudentListRequest request
    ) {
        String method = "[listStudents]";
        logger.info("📥 {} Request received: {}", method, request);

        CommonResponse<List<StudentListResponse>> response;

        try {
            // 🔍 Extract Filters
            StudentFilterRequest filter = Optional.ofNullable(request.getFilters()).orElse(new StudentFilterRequest());
            String className = filter.getClassName();
            String section = filter.getSection();
            Integer rollNumber = filter.getRollNumber();
            String search = Optional.ofNullable(request.getSearch()).map(SearchRequest::getSearchKeyword).orElse(null);

            // ⏱ Pagination & Sorting
            int page = Optional.ofNullable(request.getPagination()).map(PaginationRequest::getPage).orElse(0);
            int size = Optional.ofNullable(request.getPagination()).map(PaginationRequest::getSize).orElse(10);

            String sortBy = Optional.ofNullable(request.getSearch()).map(SearchRequest::getSortBy).orElse("firstName");
            String sortDir = Optional.ofNullable(request.getSearch()).map(SearchRequest::getSortDirection).orElse("asc");

            Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
            Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

            // 🚀 Fetch Data from JPA
            Page<Student> studentPage = studentRepository.findFilteredStudents(className, section, rollNumber, search, pageable);

            // 🔄 Map to DTO
            List<StudentListResponse> students = studentPage.getContent()
                    .stream()
                    .map(studentMapper::toListResponse)
                    .toList();

            // 📦 Response
            PaginationMeta pagination = PaginationMeta.builder()
                    .page(page)
                    .size(size)
                    .totalElements(studentPage.getTotalElements())
                    .totalPages(studentPage.getTotalPages())
                    .lastPage(studentPage.isLast())
                    .build();

            response = CommonResponse.<List<StudentListResponse>>builder()
                    .validationSuccess(true)
                    .processingSuccess(true)
                    .statusCode(200)
                    .message("Success")
                    .data(students)
                    .pagination(pagination)
                    .timestamp(LocalDateTime.now())
                    .build();

            logger.info("✅ {} Students fetched: {} | page {}/{}", method, students.size(), page, pagination.getTotalPages());

        } catch (Exception ex) {
            logger.error("❌ {} Error: {}", method, ex.getMessage(), ex);

            response = CommonResponse.<List<StudentListResponse>>builder()
                    .validationSuccess(true)
                    .processingSuccess(false)
                    .statusCode(500)
                    .message("Failed to fetch student list")
                    .data(List.of())
                    .pagination(PaginationMeta.builder().page(0).size(0).totalElements(0).totalPages(0).lastPage(true).build())
                    .debugInfo(Map.of("exception", ex.getMessage()))
                    .timestamp(LocalDateTime.now())
                    .build();
        }

        return response;
    }




}
