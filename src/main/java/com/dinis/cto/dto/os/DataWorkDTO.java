package com.dinis.cto.dto.os;


import com.dinis.cto.dto.car.DataCarDTO;
import com.dinis.cto.model.os.Work;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public  record DataWorkDTO(
        Long id,
        String description,
        @NotNull
        @Valid
        DataCarDTO car,
        List<DataPartsDTO> parts,
        BigDecimal value
) {
    public DataWorkDTO(Work work) {
        this(
                work.getId(),
                work.getDescription(),
                new DataCarDTO(work.getCar()),
                work.getParts().stream()
                        .map(DataPartsDTO::new)
                        .collect(Collectors.toList()),
                work.getValue()
        );
    }
}

