package com.dinis.cto.dto.report;

import java.math.BigDecimal;
import java.util.List;

public record FuelReportTotalsDTO(List<FuelReportDTO> reports, BigDecimal totalAmount, int totalKm) {}
