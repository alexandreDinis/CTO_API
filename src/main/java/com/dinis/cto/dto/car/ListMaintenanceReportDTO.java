package com.dinis.cto.dto.car;

import java.math.BigDecimal;
import java.util.List;

public record ListMaintenanceReportDTO(
        List<ListMaintenanceCarDTO> maintenance,
        BigDecimal totalCost
) {
}

