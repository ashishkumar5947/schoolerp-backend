package com.schoolerp.mysaas.student.service;

import com.schoolerp.mysaas.common.dto.commonresponse.CommonResponse;
import com.schoolerp.mysaas.common.dto.commonresponse.PaginationMeta;
import com.schoolerp.mysaas.common.emum.AllEnum;
import com.schoolerp.mysaas.common.emum.MessageCodeEnum;
import com.schoolerp.mysaas.common.filterRequest.PaginationRequest;
import com.schoolerp.mysaas.common.filterRequest.SearchRequest;
import com.schoolerp.mysaas.student.dto.*;
import com.schoolerp.mysaas.student.entity.Student;
import com.schoolerp.mysaas.student.repository.StudentDataAccess;
import com.schoolerp.mysaas.student.util.StudentMapper;
import com.schoolerp.mysaas.student.util.StudentNativeMapper;
import com.schoolerp.mysaas.util.CommonResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class StudentService {
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final CommonResponseUtil commonResponseUtil;
    private final StudentMapper studentMapper;
    private final StudentDataAccess studentDataAccess;

    @Autowired
    public StudentService(CommonResponseUtil commonResponseUtil, StudentMapper studentMapper, StudentDataAccess studentDataAccess) {
        this.commonResponseUtil = commonResponseUtil;
        this.studentMapper = studentMapper;
        this.studentDataAccess = studentDataAccess;
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
            studentDataAccess.saveStudent(tenantCode, channelCode, student);

            // 📤 5. Map Entity to Response DTO
            StudentCreateResponse studentResponse = studentMapper.toResponse(student);
            commonResponse = commonResponseUtil.success(tenantCode, locale, module, MessageCodeEnum.STUDENT_MC.MC_100.getCode(), studentResponse);

            logger.info("✅ Student created: ID={}, Name={} {}", student.getId(), student.getFirstName(), student.getLastName());

        } catch (Exception e) {
            commonResponse = commonResponseUtil.exceptionError(e);
        }
       
        return commonResponse;
    }

    public CommonResponse<List<StudentListResponse>> listStudents(String tenantCode, String channelCode, String locale, StudentListRequest request) {
        String method = "[listStudents]";
        logger.info("📥 {} Request received: {}", method, request);

        CommonResponse<List<StudentListResponse>> response = new CommonResponse<>();
        List<StudentListResponse> students = new ArrayList<>();
        PaginationMeta paginationMeta;

        try {
            // --- Base Queries ---
            StringBuilder sql = new StringBuilder("SELECT id, admission_no, first_name, last_name, class_name, section, roll_number FROM students WHERE 1=1 ");
            StringBuilder countSql = new StringBuilder("SELECT COUNT(*) FROM students WHERE 1=1 ");
            List<Object> params = new ArrayList<>();

            // --- Filters ---
            if (request.getFilters() != null) {
                StudentFilterRequest filter = request.getFilters();
                if (filter.getClassName() != null) {
                    sql.append("AND class_name = ? ");
                    countSql.append("AND class_name = ? ");
                    params.add(filter.getClassName());
                }
                if (filter.getSection() != null) {
                    sql.append("AND section = ? ");
                    countSql.append("AND section = ? ");
                    params.add(filter.getSection());
                }
                if (filter.getRollNumber() != null) {
                    sql.append("AND roll_number = ? ");
                    countSql.append("AND roll_number = ? ");
                    params.add(filter.getRollNumber());
                }
            }

            // --- Search ---
            if (request.getSearch() != null && request.getSearch().getSearchKeyword() != null) {
                sql.append("AND first_name ILIKE ? ");
                countSql.append("AND first_name ILIKE ? ");
                params.add("%" + request.getSearch().getSearchKeyword() + "%");
            }

            // --- Sorting ---
            String sortBy = Optional.ofNullable(request.getSearch()).map(SearchRequest::getSortBy).orElse("first_name");
            String sortDir = Optional.ofNullable(request.getSearch()).map(SearchRequest::getSortDirection).orElse("ASC");
            sql.append("ORDER BY ").append(sortBy).append(" ").append(sortDir).append(" ");

            // --- Pagination ---
            int page = Optional.ofNullable(request.getPagination()).map(PaginationRequest::getPage).orElse(0);
            int size = Optional.ofNullable(request.getPagination()).map(PaginationRequest::getSize).orElse(10);
            List<Object[]> rows = studentDataAccess.fetchRowsWithPagination(sql.toString(), page, size, params.toArray());
            students = rows.stream().map(StudentNativeMapper::map).toList();

            // --- Count Query ---
            Long total = studentDataAccess.fetchCount(countSql.toString(), params.toArray());

            paginationMeta = PaginationMeta.builder()
                    .page(page)
                    .size(size)
                    .totalElements(total)
                    .totalPages((int) Math.ceil((double) total / size))
                    .lastPage((long) (page + 1) * size >= total)
                    .build();

            // --- Success Response ---
            response = CommonResponse.<List<StudentListResponse>>builder()
                    .validationSuccess(true)
                    .processingSuccess(true)
                    .statusCode(200)
                    .message("Success")
                    .data(students)
                    .pagination(paginationMeta)
                    .timestamp(LocalDateTime.now())
                    .build();

            logger.info("✅ {} Students fetched successfully | count={}, page={}/{}", method, students.size(), page, paginationMeta.getTotalPages());

        } catch (Exception ex) {
            logger.error("❌ {} Error fetching student list: {}", method, ex.getMessage(), ex);

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
