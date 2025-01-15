package com.dinis.cto.dto.person;

import com.dinis.cto.model.person.Contact;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public record DataContactDTO(
                             @NotBlank
                             String name,

                             String department,

                             @NotNull
                             List<DataPhoneDTO> phones,

                             @NotBlank
                             @Email
                             String email) {
    public DataContactDTO(Contact contact) {
        this(contact.getName(),
                contact.getDepartment(),
                contact.getPhones().stream()
                        .map(DataPhoneDTO::new)
                        .collect(Collectors.toList()),
                contact.getEmail());
    }
}
