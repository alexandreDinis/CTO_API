package com.dinis.cto.controller;

import com.dinis.cto.dto.car.DataFuelDTO;
import com.dinis.cto.service.FuelService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("fuel")
@SecurityRequirement(name = "bearer-key")
public class FuelController {

    @Autowired
    private FuelService service;

    @PostMapping("/open")
    @Transactional
    public ResponseEntity<DataFuelDTO> openFuel(@RequestBody @Valid DataFuelDTO data) {

        service.openFuel(data);

        return ResponseEntity.ok().build();
    }



}
