package com.schoolerp.mysaas.student.dto;

import com.schoolerp.mysaas.common.filterRequest.PaginationRequest;
import com.schoolerp.mysaas.common.filterRequest.SearchRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentListRequest {
    private PaginationRequest pagination;
    private StudentFilterRequest filters;
    private SearchRequest search;
}
