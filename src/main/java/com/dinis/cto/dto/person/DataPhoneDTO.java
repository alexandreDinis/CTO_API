package com.dinis.cto.dto.person;

import com.dinis.cto.model.person.Phone;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DataPhoneDTO(Long id,String description,
                           @NotBlank @Pattern(regexp = "\\(\\d{2}\\) \\d{4,5}-\\d{4}", message =
                                   "Telefone deve estar no formato (XX) XXXX-XXXX ou (XX) XXXXX-XXXX")
                           String number) {

    public DataPhoneDTO(Phone phone) {
        this(phone.getId(), phone.getDescription(), phone.getNumber());
    }
}

