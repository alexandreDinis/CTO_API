package com.dinis.cto.dto.car;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;


//retrona os dados
public record ExistingMaintenanceDetailsDTO(
        @NotNull String local,
        @NotNull BigDecimal value,
        @NotNull int initKm,
        @NotNull int durationKm
) {}
