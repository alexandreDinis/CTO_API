package com.dinis.cto.dto.report;

import java.math.BigDecimal;
import java.util.List;

public record MonthlyComparisonReportDTO(List<MonthlyComparisonDTO> monthlyComparisons, BigDecimal totalYear1,
        BigDecimal totalYear2, BigDecimal totalBalance, BigDecimal totalPercentage) {}
