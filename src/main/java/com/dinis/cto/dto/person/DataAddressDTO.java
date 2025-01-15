package com.dinis.cto.dto.person;

import com.dinis.cto.model.person.Address;
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

        public DataAddressDTO(Address address) {
                this(address.getStreet(),
                        address.getNumber(),
                        address.getNeighborhood(),
                        address.getCity(),
                        address.getState(),
                        address.getZipCode(),
                        address.getComplement());
        }
}
