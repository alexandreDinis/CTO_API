package com.dinis.cto.dto.person;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record DataContactDTO(
                             @NotBlank
                             String name,

                             String department,

                             @NotNull
                             List<DataPhoneDTO> phones,

                             @NotBlank
                             @Email
                             String email) {
}
