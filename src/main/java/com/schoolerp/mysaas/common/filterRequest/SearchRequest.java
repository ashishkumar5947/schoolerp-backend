package com.schoolerp.mysaas.common.filterRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchRequest {
    private String searchKeyword;     // e.g., "Sujan"
    private String sortBy;            // e.g., "firstName"
    private String sortDirection;     // ASC or DESC
}
