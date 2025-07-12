package com.schoolerp.mysaas.student.util;

import com.schoolerp.mysaas.student.dto.StudentCreateRequest;
import com.schoolerp.mysaas.student.dto.StudentCreateResponse;
import com.schoolerp.mysaas.student.dto.StudentListResponse;
import com.schoolerp.mysaas.student.entity.Student;

public interface StudentMapper {
    Student toEntity(StudentCreateRequest request);
    StudentCreateResponse toResponse(Student student);
    StudentListResponse toListResponse(Student student);
}
