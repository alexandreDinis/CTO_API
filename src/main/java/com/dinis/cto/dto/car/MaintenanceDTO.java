package com.dinis.cto.dto.car;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MaintenanceDTO(Long id, String description, LocalDate createDate, BigDecimal value
) {
}

