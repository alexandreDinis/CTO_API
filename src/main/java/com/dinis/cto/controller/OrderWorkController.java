package com.dinis.cto.controller;


import com.dinis.cto.dto.os.*;
import com.dinis.cto.service.OrderWorkService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("os")
@SecurityRequirement(name = "bearer-key")
public class OrderWorkController {

    @Autowired
    private OrderWorkService service;


    @PostMapping("/open")
    @Transactional
    public ResponseEntity<DataOrderWorkDTO> openOS(@RequestBody @Valid DataOrderWorkDTO data){

        service.openOS(data);

        return ResponseEntity.ok().build();
    }

    @GetMapping("list/opens")
    public Page<ResponseOsTrueDTO> listStatusTrue(Pageable pageable) {

        return service.listStatusTrue(pageable);
    }

    @GetMapping("list/closed")
    public Page<ResponseOsFalseDTO> listStatusFalse(Pageable pageable) {
        return service.listStatusFalse(pageable);
    }
    @GetMapping("{id}")
    public ResponseEntity<DetailOsDTO> listOsDTO(@PathVariable Long id ) {

        var orderWork = service.getOrderWorkDetails(id);

        return ResponseEntity.ok(orderWork);
    }
    @PutMapping("{id}/discount")
    @Transactional
    public ResponseEntity<?> applyDiscount(@PathVariable Long id, @RequestBody DataOsDiscountDTO data) {

        service.applyDiscount(id, data);

        return ResponseEntity.ok().build();
    }

}
