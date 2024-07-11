package com.dinis.cto.dto.person;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DataAddressDTO(

        @NotBlank
        String street,

        String number,

        String neighborhood,

        @NotBlank
        String city,

        @NotBlank
        String state,

        @NotBlank(message = "CEP não pode ser vazio")
        @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "Formato de CEP inválido")
        String zipCode,
        String complement) {
}
