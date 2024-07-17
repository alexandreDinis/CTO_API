package com.dinis.cto.dto.person;

import com.dinis.cto.model.os.OrderWork;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ResponseOsFalseDTO(Long id, String fantasyName, String City, LocalDate createDate, BigDecimal value) {

    public ResponseOsFalseDTO(OrderWork data) {
        this(data.getId(), data.getClient().getFantasyName(),data.getClient().getAddress().getCity(),
               data.getCreateDate(), data.getValueTotal() );
    }
}
