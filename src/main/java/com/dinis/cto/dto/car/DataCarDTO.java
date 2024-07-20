package com.dinis.cto.dto.car;

import com.dinis.cto.model.car.Car;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record DataCarDTO(



        @NotBlank
        String make,

        @NotBlank
        String model,

        @NotBlank
        String plate,


        String year,

        int initialKm,


        LocalDate createDate
) {
        public DataCarDTO(Car data){
                this( data.getMake(), data.getModel(), data.getPlate(), data.getYear(),
                        data.getInitialKm(), data.getCreateDate());
        }
}


