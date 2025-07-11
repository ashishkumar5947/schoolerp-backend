package com.schoolerp.mysaas.common.filterRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginationRequest {
    private Integer page = 0;
    private Integer size = 10;
}
