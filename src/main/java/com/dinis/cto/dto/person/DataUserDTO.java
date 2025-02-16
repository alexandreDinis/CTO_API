package com.dinis.cto.dto.person;

import com.dinis.cto.model.person.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DataUserDTO(
        @NotNull @Valid DataContactDTO contact,
        @Pattern(regexp = "\\d{11}", message = "CPF Inválido.") @NotBlank String cpf,
        @NotBlank @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$", message = "A data deve estar no formato dd/MM/yyyy") String dateBirth,
        @Valid @NotNull DataAddressDTO address,
        @NotBlank @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\\$%\\^&\\*])(?=\\S+$).{8,}$",
                message = "A senha deve ter no mínimo 8 caracteres, com pelo menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial") String password

) {
    public DataUserDTO(User user) {
        this(
                new DataContactDTO(user.getContact()), // Converte Contact para DataContactDTO
                user.getCpf(),
                user.getDateBirth(),
                new DataAddressDTO(user.getAddress()), // Converte Address para DataAddressDTO
                user.getPassword()
        );
    }
}


