package com.dinis.cto.dto.person;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DataPasswordRecoveryDTO(
        @NotBlank @Email String email
) {}
