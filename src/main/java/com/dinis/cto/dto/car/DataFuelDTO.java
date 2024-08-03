package com.dinis.cto.dto.car;

import com.dinis.cto.model.car.TypeFuel;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DataFuelDTO(

        @NotNull
        Long carID,

        @NotNull
        TypeFuel typeFuel,

        @NotNull
        BigDecimal fuelPrice,

        @NotNull
        BigDecimal amount,

        @NotNull
        Integer km){


}
