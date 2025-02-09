package com.dinis.cto.dto.person;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DataPasswordResetDTO(
        @NotBlank String token,
        @NotBlank @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\\$%\\^&\\*])(?=\\S+$).{8,}$", message = "A senha deve atender aos critérios de segurança") String newPassword
) {}
