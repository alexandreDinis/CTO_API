package com.dinis.cto.dto.person;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO(@NotBlank String user, @NotBlank String password) {
}
