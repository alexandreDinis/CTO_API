package com.dinis.cto.controller;


import com.dinis.cto.dto.car.DataMaintenanceDTO;
import com.dinis.cto.dto.car.ExistingMaintenanceDetailsDTO;
import com.dinis.cto.dto.car.ListMaintenanceCarDTO;
import com.dinis.cto.service.MaintenanceService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("maintenance")
@SecurityRequirement(name = "bearer-key")
public class MaintenanceController {

    @Autowired
    private MaintenanceService service;

    @PostMapping("/open")
    @Transactional
    public ResponseEntity<?> openMaintenance(DataMaintenanceDTO data) {

        service.openMaintenance(data);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<ExistingMaintenanceDetailsDTO> doMaintenance(@PathVariable Long id, @RequestBody ExistingMaintenanceDetailsDTO data) {

        service.doMaintenance(id, data);
        return ResponseEntity.noContent().build();
    }
}
