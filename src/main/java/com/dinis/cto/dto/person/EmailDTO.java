package com.dinis.cto.dto.person;

import jakarta.validation.constraints.Email;

public record EmailDTO(
        @Email(message = "O e-mail informado não é válido") String email
) {}
