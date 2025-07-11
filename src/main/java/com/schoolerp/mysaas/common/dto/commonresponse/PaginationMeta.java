package com.schoolerp.mysaas.common.dto.commonresponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginationMeta {
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean lastPage;
}
