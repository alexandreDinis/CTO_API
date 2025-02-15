package com.dinis.cto.dto.person;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DataUserUpdateDTO(
        @Valid DataContactDTO contact,
        @Pattern(regexp = "\\d{11}", message = "CPF Inválido.") String cpf,
        @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$", message = "A data deve estar no formato dd/MM/yyyy") String dateBirth,
        @Valid DataAddressDTO address
) {}

