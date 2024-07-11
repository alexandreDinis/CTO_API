package com.dinis.cto.dto.person;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DataPhoneDTO(
                           String description,

                           @NotBlank
                           @Pattern(regexp = "\\(\\d{2}\\) \\d{4,5}-\\d{4}", message =
                                   "Telefone deve estar no formato (XX) XXXX-XXXX ou (XX) XXXXX-XXXX")
                           String number) {}

