package com.dinis.cto.dto.report;

import java.math.BigDecimal;

public record FuelReportDTO(String type, BigDecimal averagePrice, BigDecimal totalAmount, Integer totalKm,
        BigDecimal autonomy
) {

    @Override
    public String toString() {
        return "Tipo: " + type + "\n" +
                "Media de preço R$" + averagePrice + "\n" +
                "Total de km: " + totalKm + "\n" +
                "Total Abastecido R$" + totalAmount + "\n" +
                "Autonomia: " + autonomy;
    }

    public void printReport() {
        System.out.println(this);
    }
}
