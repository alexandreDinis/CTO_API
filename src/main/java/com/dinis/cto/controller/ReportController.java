package com.dinis.cto.controller;


import com.dinis.cto.dto.os.DateRangeDTO;
import com.dinis.cto.dto.os.PaginatedResponseWithTotal;
import com.dinis.cto.dto.os.ResponseOsFalseDTO;
import com.dinis.cto.dto.report.*;
import com.dinis.cto.service.ReportService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reports")
@SecurityRequirement(name = "bearer-key")
public class ReportController {

    @Autowired
    private ReportService service;

    @PostMapping("/os/date")
    public ResponseEntity<PaginatedResponseWithTotal<ResponseOsFalseDTO>> listClosedOrdersWithinPeriod(
            @RequestBody DateRangeDTO dateRangeDTO) {
        PaginatedResponseWithTotal<ResponseOsFalseDTO> response = service.listClosedOrdersWithinPeriod(dateRangeDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/parts-count")
    public ResponseEntity<ReportDataDTO> countPartsMonth(@RequestBody PeriodDTO periodDTO) {
        ReportDataDTO report = service.countPartsMonth(periodDTO);
        return ResponseEntity.ok(report);
    }

    // melhorar ele esta retornando apenas a quantidade, temos que retornar igual parts.
    @PostMapping("/cars-count")
    public ResponseEntity<ClientCarReportDTO> getClientCarReport(@RequestBody PeriodDTO periodDTO) {
        ClientCarReportDTO report = service.generateClientCarReport(periodDTO);
        return ResponseEntity.ok(report);
    }

    @PostMapping("/client-attendance")
    public ResponseEntity<ClientAttendanceReportDTO> getClientAttendanceReport(@RequestBody PeriodDTO periodDTO) {
        ClientAttendanceReportDTO report = service.generateClientAttendanceReport(periodDTO);
        return ResponseEntity.ok(report);
    }

    // esta retornando, agora precisa criar outras empresas para ver se tudo funciona
    @GetMapping("client-ranking")
    public ResponseEntity<List<ClientRankingDTO>> getClientRanking(@RequestBody PeriodDTO data) {

        List<ClientRankingDTO> ranking = service.getClientRanking(data.year(), data.month());
        return ResponseEntity.ok(ranking);
    }


    //Falta testar
    @PostMapping("/monthly-comparison")
    public ResponseEntity<MonthlyComparisonReportDTO> generateMonthlyComparisonReport(
            @RequestBody PeriodComparisonDTO periodComparisonDTO) {

        int year1 = periodComparisonDTO.year1();
        int year2 = periodComparisonDTO.year2();

        MonthlyComparisonReportDTO report = service.generateMonthlyComparisonReport(year1, year2);

        return ResponseEntity.ok(report);
    }
}
