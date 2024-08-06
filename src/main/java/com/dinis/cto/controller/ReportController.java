package com.dinis.cto.controller;


import com.dinis.cto.dto.car.ListMaintenanceCarDTO;
import com.dinis.cto.dto.car.ListMaintenanceReportDTO;
import com.dinis.cto.dto.os.DateRangeDTO;
import com.dinis.cto.dto.os.PaginatedResponseWithTotal;
import com.dinis.cto.dto.os.ResponseOsFalseDTO;
import com.dinis.cto.dto.report.*;
import com.dinis.cto.service.ReportService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reports")
@SecurityRequirement(name = "bearer-key")
public class ReportController {

    @Autowired
    private ReportService service;


    //Lista as O.S fechadas por range de datas
    @PostMapping("/os/date")
    @Transactional(readOnly = true)
    public ResponseEntity<PaginatedResponseWithTotal<ResponseOsFalseDTO>> listClosedOrdersWithinPeriod(
            @RequestBody DateRangeDTO dateRangeDTO) {
        PaginatedResponseWithTotal<ResponseOsFalseDTO> response = service.listClosedOrdersWithinPeriod(dateRangeDTO);
        return ResponseEntity.ok(response);
    }

    //Lista a quantidade de peças, valor e media por rang de data
    @PostMapping("/parts-count")
    @Transactional(readOnly = true)
    public ResponseEntity<ReportDataDTO> countPartsInRange(@RequestBody DateRangeDTO dateRangeDTO) {
        ReportDataDTO report = service.generatePartsReport(dateRangeDTO);
        return ResponseEntity.ok(report);
    }

    //Mostra a quantidade de carros feito no mes
    @PostMapping("/cars-count")
    public ResponseEntity<ClientCarReportDTO> getClientCarReport(@RequestBody PeriodDTO periodDTO) {
        ClientCarReportDTO report = service.generateClientCarReport(periodDTO);
        return ResponseEntity.ok(report);
    }

    //Mostra a quantidade de cliente atendido por mes
    @PostMapping("/client-attendance")
    public ResponseEntity<ClientAttendanceReportDTO> getClientAttendanceReport(@RequestBody PeriodDTO periodDTO) {
        ClientAttendanceReportDTO report = service.generateClientAttendanceReport(periodDTO);
        return ResponseEntity.ok(report);
    }

    // retorna o ranking de cliente por ano ou mes
    @GetMapping("client-ranking")
    public ResponseEntity<List<ClientRankingDTO>> getClientRanking(@RequestBody PeriodDTO data) {

        List<ClientRankingDTO> ranking = service.getClientRanking(data.year(), data.month());
        return ResponseEntity.ok(ranking);
    }


    //relarorio de comparação de vendas de um ano para o outro completo
    @PostMapping("/monthly-comparison")
    public ResponseEntity<MonthlyComparisonReportDTO> generateMonthlyComparisonReport(
            @RequestBody PeriodComparisonDTO periodComparisonDTO) {

        int year1 = periodComparisonDTO.year1();
        int year2 = periodComparisonDTO.year2();

        MonthlyComparisonReportDTO report = service.generateMonthlyComparisonReport(year1, year2);

        return ResponseEntity.ok(report);
    }

    //Todo: testar
    //Gera a lista de manutenção aberta

    @GetMapping("/maintenance=true")
    public ResponseEntity<Page<ListMaintenanceCarDTO>> getMaintenancesWithStatusTrue(Pageable pageable) {
        Page<ListMaintenanceCarDTO> maintenancePage = service.getMaintenancesWithStatusTrue(pageable);
        return ResponseEntity.ok(maintenancePage);
    }

    //Todo: testar
    @GetMapping("/maintenances/status-false")
    public ResponseEntity<ListMaintenanceReportDTO> getMaintenancesWithStatusFalseAndTotal(
            @RequestParam(value = "year", required = false) Integer year,
            Pageable pageable) {
        ListMaintenanceReportDTO report = service.getMaintenancesWithStatusFalseAndTotal(year, pageable);
        return ResponseEntity.ok(report);
    }

    //relatorio de abastecimento por range de data
    @PostMapping("/fuel")
    public ResponseEntity<FuelReportTotalsDTO> getFuelReport(@RequestBody DateRangeDTO dateRangeDTO, @RequestParam Long userCarId) {
        FuelReportTotalsDTO reportTotals = service.generateFuelReport(dateRangeDTO, userCarId);
        return ResponseEntity.ok(reportTotals);
    }
}
