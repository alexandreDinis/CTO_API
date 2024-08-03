package com.dinis.cto.dto.car;

import java.math.BigDecimal;
import java.util.Map;

public record ReportFuelDTO(
        String month,
        Map<String, FuelReport> fuelReports,
        BigDecimal totalValue
) {
}