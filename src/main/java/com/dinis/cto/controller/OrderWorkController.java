package com.dinis.cto.controller;


import com.dinis.cto.dto.os.*;
import com.dinis.cto.service.OrderWorkService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<PaginatedResponseWithTotal<ResponseOsTrueDTO>> listStatusTrue(Pageable pageable) {
        PaginatedResponseWithTotal<ResponseOsTrueDTO> response = service.listStatusTrue(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("list/closed")
    public ResponseEntity<PaginatedResponseWithTotal<ResponseOsFalseDTO>> listStatusFalse(Pageable pageable) {
        PaginatedResponseWithTotal<ResponseOsFalseDTO> response = service.listStatusFalse(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<DetailOsDTO> listDetailsOs(@PathVariable Long id ) {

        var orderWork = service.getOrderWorkDetails(id);

        return ResponseEntity.ok(orderWork);
    }

    @PutMapping("{id}/discount")
    @Transactional
    public ResponseEntity<?> applyDiscount(@PathVariable Long id, @RequestBody DataOsDiscountDTO data) {

        service.applyDiscount(id, data);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<?> closeOs (@PathVariable Long id) {

        service.closeOs(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("search-plate")
    public ResponseEntity<List<DetailOsDTO>> getOrderWorksByClientAndCarPlate(@RequestBody ClientCarSearchDTO searchDTO) {

        List<DetailOsDTO> orderWorkDTOs = service.findOrderWorksByClientAndCarPlate(searchDTO);
        return ResponseEntity.ok(orderWorkDTOs);
    }
}
