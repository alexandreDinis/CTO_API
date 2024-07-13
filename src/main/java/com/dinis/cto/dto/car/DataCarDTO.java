package com.dinis.cto.dto.car;

import com.dinis.cto.model.car.Car;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DataCarDTO(

        Long id,

        @NotBlank
        String make,

        @NotBlank
        String model,

        @NotBlank
        String place,


        String year,

        int initialKm,

        @NotNull
        LocalDate createDate
) {
        public DataCarDTO(Car data){
                this(data.getId(), data.getMake(), data.getModel(), data.getPlace(), data.getYear(),
                        data.getInitialKm(), data.getCreateDate());
        }
}


