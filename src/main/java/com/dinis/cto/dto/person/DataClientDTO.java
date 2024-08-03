package com.dinis.cto.dto.person;

import com.dinis.cto.model.person.Address;
import com.dinis.cto.model.person.Client;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public record DataClientDTO(

         Long id,

         @NotBlank
         String fantasyName,
         String corporateReason,

         Boolean status,

         @NotNull
         @Valid
         DataAddressDTO address,

         String cpf,
         String cnpj,

         @NotNull
         LocalDate createDate,

         @NotNull
         @Valid
         List<DataContactDTO> contacts

) {public DataClientDTO(Client data) {
    this(data.getId(),
            data.getFantasyName(),
            data.getCorporateReason(),
            data.getStatus(),
            new DataAddressDTO(data.getAddress()),
            data.getCpf(),
            data.getCnpj(),
            data.getCreateDate(),
            data.getContacts().stream()
                    .map(DataContactDTO::new)
                    .collect(Collectors.toList()));
}
}
