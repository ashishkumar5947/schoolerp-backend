package com.schoolerp.mysaas.student.util;

import com.schoolerp.mysaas.student.dto.StudentListResponse;

import java.util.UUID;

public class StudentNativeMapper {

    public static StudentListResponse map(Object[] row) {
        return StudentListResponse.builder()
                .id(row[0] != null ? UUID.fromString(row[0].toString()) : null)
                .admissionNo((String) row[1])
                .firstName((String) row[2])
                .lastName((String) row[3])
                .className((String) row[4])
                .section((String) row[5])
                .rollNumber(row[6] != null ? Integer.parseInt(row[6].toString()) : null)
                .build();
    }
}
