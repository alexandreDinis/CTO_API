package com.dinis.cto.dto.os;

import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public record PaginatedResponseWithTotal<T>(Page<T> page, List<T> list, BigDecimal totalValue) {

    public PaginatedResponseWithTotal(Page<T> page, BigDecimal totalValue) {
        this(page, null, totalValue);
    }

    public PaginatedResponseWithTotal(List<T> list, BigDecimal totalValue) {
        this(null, list, totalValue);
    }
}