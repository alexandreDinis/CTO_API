package com.dinis.cto.dto.os;

import com.dinis.cto.model.os.OrderWork;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ResponseOsTrueDTO(Long id, String fantasyName, String City, LocalDate createDate, BigDecimal value) {

    public ResponseOsTrueDTO(OrderWork data) {
        this(data.getId(), data.getClient().getFantasyName(),data.getClient().getAddress().getCity(),
               data.getCreateDate(), data.getServiceValue() );
    }
}
