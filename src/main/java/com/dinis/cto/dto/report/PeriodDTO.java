package com.dinis.cto.dto.report;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PeriodDTO(
        Integer year,
        Integer month
) {
}
