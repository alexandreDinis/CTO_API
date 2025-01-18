package com.dinis.cto.dto.car;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DataMaintenanceDTO(@NotNull Long carID, @NotBlank String description, @NotBlank String local,
        @NotNull BigDecimal value, @NotNull int initKm, @NotNull int durationKm
) {
}
