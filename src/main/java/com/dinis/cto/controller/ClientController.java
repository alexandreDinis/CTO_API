package com.dinis.cto.controller;

import com.dinis.cto.dto.person.ClientSummary;
import com.dinis.cto.dto.person.DataClientDTO;
import com.dinis.cto.service.ClientService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("client")
@SecurityRequirement(name = "bearer-key")
public class ClientController {

    @Autowired
    private ClientService service;


    @PostMapping("/register")
    @Transactional
    public ResponseEntity<DataClientDTO> register (@RequestBody @Valid DataClientDTO data) {
        service.register(data);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public Page<ClientSummary> list(Pageable pageable) {
        return service.list(pageable);
    }

    @GetMapping("{id}")
    public ResponseEntity<DataClientDTO> details(@PathVariable Long id) {
        var client = service.details(id);
        return ResponseEntity.ok(client);
    }
}

