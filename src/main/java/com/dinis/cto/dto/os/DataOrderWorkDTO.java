package com.dinis.cto.dto.os;

import com.dinis.cto.model.os.Work;
import com.dinis.cto.model.person.Client;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record DataOrderWorkDTO(

         Long id,

         @NotNull
         LocalDate createDate,

         @NotNull
         int initKm,

         @NotNull
         int finalKm,

         @NotNull
         Client client,

         List<Work> works,

         BigDecimal serviceValue,
         BigDecimal discountValue,
         BigDecimal discountPercentage,
         BigDecimal valueTotal
) {
}
