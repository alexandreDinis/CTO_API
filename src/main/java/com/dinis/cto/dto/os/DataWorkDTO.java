package com.dinis.cto.dto.os;


import com.dinis.cto.model.os.Work;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public  record DataWorkDTO(
        Long id,
        String description,
        List<DataPartsDTO> parts,
        BigDecimal value
) {
    public DataWorkDTO(Work work) {
        this(
                work.getId(),
                work.getDescription(),
                work.getParts().stream()
                        .map(DataPartsDTO::new)
                        .collect(Collectors.toList()),
                work.getValue()
        );
    }
}

