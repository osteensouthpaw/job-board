package com.omega.jobportal.utils;


import org.springframework.data.domain.Page;

import java.util.List;

public record PageResponse<T>(
        List<T> content,
        int pageNumber,
        int pageSize,
        int totalPages,
        long totalElements,
        boolean first,
        boolean last,
        long offset
) {

    public PageResponse(Page<T> page) {
        this(page.getContent(),
                page.getNumber(),
                page.getSize(), page.getTotalPages(),
                page.getTotalElements(),
                page.isFirst(),
                page.isLast(),
                page.getNumberOfElements()
        );
    }

}
