package com.dinis.cto.dto.report;

import java.math.BigDecimal;

public record ClientRankingDTO(String fantasyName, BigDecimal totalValue) {
    public ClientRankingDTO(String fantasyName, BigDecimal totalValue) {
        this.fantasyName = fantasyName;
        this.totalValue = totalValue;
    }
}
