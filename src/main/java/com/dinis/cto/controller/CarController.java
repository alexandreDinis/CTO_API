package com.dinis.cto.controller;

import com.dinis.cto.dto.car.DataCarDTO;
import com.dinis.cto.service.CarService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("car")
@SecurityRequirement(name = "bearer-key")
public class CarController {

    @Autowired
    private CarService service;

    @PostMapping("/register/user")
    @Transactional
    public ResponseEntity<DataCarDTO> register(@RequestBody @Valid DataCarDTO data) {

        service.registerUserCar(data);

        return ResponseEntity.ok().build();
    }
    @GetMapping
    public Page<DataCarDTO> userCarList(Pageable pageable) {
        return service.userCarList(pageable);
    }
}
