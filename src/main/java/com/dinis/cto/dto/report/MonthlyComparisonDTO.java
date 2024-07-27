package com.dinis.cto.dto.report;

import java.math.BigDecimal;

public record MonthlyComparisonDTO(
        String month,
        BigDecimal year1Total,
        BigDecimal year2Total,
        BigDecimal balance,
        BigDecimal percentage
) {}
