package com.dinis.cto.dto.os;

import com.dinis.cto.model.os.Parts;

import java.math.BigDecimal;

public record DataPartsDTO(Long id, String name, String description, BigDecimal value
) {
    public DataPartsDTO(Parts parts) {
        this(
                parts.getId(),
                parts.getName(),
                parts.getDescription(),
                parts.getValue()
        );
    }
}