package com.dinis.cto.dto.os;

import com.dinis.cto.dto.person.ClientSummary;
import com.dinis.cto.model.os.BudgetEnum;
import com.dinis.cto.model.os.OrderWork;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public record DetailOsDTO(Long id, BudgetEnum budget, LocalDate createDate, Boolean status, ClientSummary client,
        List<DataWorkDTO> works, BigDecimal serviceValue, BigDecimal discountValue, BigDecimal discountPercentage,
        BigDecimal valueTotal
) {
    public DetailOsDTO(OrderWork orderWork) {
        this(
                orderWork.getId(),
                orderWork.getBudget(),
                orderWork.getCreateDate(),
                orderWork.getStatus(),
                new ClientSummary(orderWork.getClient().getId(),orderWork.getClient().getFantasyName(), orderWork.getClient().getAddress().getCity()),
                orderWork.getWorks().stream()
                        .map(DataWorkDTO::new)
                        .collect(Collectors.toList()),
                orderWork.getServiceValue(),
                orderWork.getDiscountValue(),
                orderWork.getDiscountPercentage(),
                orderWork.getValueTotal()
        );
    }
}
