package com.dinis.cto.dto.report;

import java.math.BigDecimal;

public record ReportDataDTO(
        long quantity,
        BigDecimal totalValue,
        BigDecimal averageValue
) {
}
