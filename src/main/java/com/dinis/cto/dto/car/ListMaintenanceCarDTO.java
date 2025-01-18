package com.dinis.cto.dto.car;


import java.util.List;

public record ListMaintenanceCarDTO(Long id, String make, String model, String plate, int initialKm,
        List<MaintenanceDTO> maintenances
) {
}