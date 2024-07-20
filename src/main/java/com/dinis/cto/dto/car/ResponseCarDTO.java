package com.dinis.cto.dto.car;

import jakarta.validation.constraints.NotBlank;

public record ResponseCarDTO(Long id, String make, String model, String plate) {
}
