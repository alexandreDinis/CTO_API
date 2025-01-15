package com.dinis.cto.dto.person;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record DataUserDTO(

        @NotNull
        @Valid
        DataContactDTO contact,

        @Pattern(regexp = "\\d{11}", message = "CPF Invalido.")
        @NotBlank
        String cpf,

        @NotBlank
        @Pattern(
                regexp = "^\\d{2}/\\d{2}/\\d{4}$",
                message = "A data deve estar no formato dd/MM/yyyy"
        )
        String dateBirth,

        @Valid
        @NotNull
        DataAddressDTO address,

        @NotBlank
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\\$%\\^&\\*])(?=\\S+$).{8,}$",
                message = "A senha deve ter no mínimo 8 caracteres, com pelo menos uma letra maiúscula," +
                        " uma letra minúscula, um número e um caractere especial"
        )
        String password,

        @NotNull
        LocalDate createDate) {
}
