package com.dinis.cto.controller;


import com.dinis.cto.dto.os.DataOrderWorkDTO;
import com.dinis.cto.service.OrderWorkService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("os")
@SecurityRequirement(name = "bearer-key")
public class OrderWorkController {

    @Autowired
    private OrderWorkService service;


    public ResponseEntity<DataOrderWorkDTO> register(@RequestBody @Valid DataOrderWorkDTO data){

        service.register(data);

        return ResponseEntity.ok().build();
    }

}
