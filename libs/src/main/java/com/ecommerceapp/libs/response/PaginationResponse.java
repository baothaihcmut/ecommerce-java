package com.ecommerceapp.libs.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginationResponse {
    private Integer offset;
    private Integer limit;
    private Integer total;
    private Boolean hasNext;
    private Boolean hasPrev;
    private Integer nextOffset;
    private Integer prevOffset;

    public static PaginationResponse initPaginationResponse(
            Integer offset,
            Integer limit,
            Integer count) {
        Boolean hasNext = offset + limit < count;
        Boolean hasPrev = offset > 0;
        return PaginationResponse.builder()
                .offset(offset)
                .limit(limit)
                .total(count)
                .hasNext(hasNext)
                .hasPrev(hasPrev)
                .prevOffset(offset - limit)
                .nextOffset(offset + limit)
                .build();

    }
}
