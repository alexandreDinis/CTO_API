package com.dinis.cto.dto.os;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record DataOrderWorkDTO(

         Long id,

         @NotNull
         LocalDate createDate,


         int initKm,


         int finalKm,

         @NotNull
         Long clientId,

         List<DataWorkDTO> works

) {
}
