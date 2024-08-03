package com.dinis.cto.dto.car;

import java.math.BigDecimal;

public record FuelReport(
        BigDecimal averagePrice,
        BigDecimal totalAmount,
        BigDecimal totalValue
) {
}
