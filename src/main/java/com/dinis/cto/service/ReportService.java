package com.dinis.cto.service;


import com.dinis.cto.dto.os.DateRangeDTO;
import com.dinis.cto.dto.os.PaginatedResponseWithTotal;
import com.dinis.cto.dto.os.ResponseOsFalseDTO;
import com.dinis.cto.dto.report.*;
import com.dinis.cto.model.os.OrderWork;
import com.dinis.cto.model.os.Parts;
import com.dinis.cto.repository.ClientCarRepository;
import com.dinis.cto.repository.OrderWorkRepository;
import com.dinis.cto.repository.PartsRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private OrderWorkRepository orderWorkRepository;

    @Autowired
    private OrderWorkService service;

    @Autowired
    private PartsRepository partsRepository;

    @Autowired
    private ClientCarRepository clientCarRepository;

    @Transactional(readOnly = true)
    public PaginatedResponseWithTotal<ResponseOsFalseDTO> listClosedOrdersWithinPeriod(DateRangeDTO dateRangeDTO) {
        LocalDate startDate = dateRangeDTO.startDate();
        LocalDate endDate = dateRangeDTO.endDate();

        List<OrderWork> orderWorks = orderWorkRepository.findClosedOrdersWithinPeriod(startDate, endDate);
        List<ResponseOsFalseDTO> responseOsFalseDTOList = orderWorks.stream()
                .map(ResponseOsFalseDTO::new)
                .collect(Collectors.toList());

        BigDecimal totalValue = service.calculateTotalServiceValue(orderWorks);

        return new PaginatedResponseWithTotal<>(responseOsFalseDTOList, totalValue);
    }

    public ReportDataDTO countPartsMonth(PeriodDTO periodDTO) {
        Integer year = periodDTO.year();
        Integer month = periodDTO.month();

        List<OrderWork> orderWorks = orderWorkRepository.findOrderWorksByYearAndMonth(year, month);

        List<Parts> parts = orderWorks.stream()
                .flatMap(orderWork -> orderWork.getWorks().stream())
                .flatMap(work -> work.getParts().stream())
                .toList();

        BigDecimal totalValue = parts.stream()
                .map(Parts::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal averageValue = parts.isEmpty() ? BigDecimal.ZERO
                : totalValue.divide(new BigDecimal(parts.size()), RoundingMode.HALF_UP);

        long quantity = parts.size();

        return new ReportDataDTO(quantity, totalValue, averageValue);
    }

    public ClientCarReportDTO generateClientCarReport(PeriodDTO periodDTO) {
        Integer year = periodDTO.year();
        Integer month = periodDTO.month();

        long count = clientCarRepository.countClientCarsByYearAndMonth(year, month);

        return new ClientCarReportDTO(count);
    }

    public ClientAttendanceReportDTO generateClientAttendanceReport(PeriodDTO periodDTO) {
        Integer year = periodDTO.year();
        Integer month = periodDTO.month();

        long clientCount = orderWorkRepository.countDistinctClientsByYearAndMonth(year, month);

        return new ClientAttendanceReportDTO(clientCount);
    }

    @Transactional(readOnly = true)
    public List<ClientRankingDTO> getClientRanking(Integer year, Integer month) {
        List<Map<String, Object>> results;

        if (year == null) {
            throw new IllegalArgumentException("Ano deve ser fornecido.");
        }

        if (month != null) {
            // Caso ano e mês sejam fornecidos
            results = orderWorkRepository.findTotalValueByClientAndMonth(year, month);
        } else {
            // Caso apenas o ano seja fornecido
            results = orderWorkRepository.findTotalValueByClientAndYear(year);
        }

        return results.stream()
                .map(result -> new ClientRankingDTO(
                        (String) result.get("fantasyName"),
                        (BigDecimal) result.get("totalValue")
                ))
                .sorted(Comparator.comparing(ClientRankingDTO::totalValue).reversed())
                .collect(Collectors.toList());
    }
    //Compara o valor total de os de um ano para outro

    public MonthlyComparisonReportDTO generateMonthlyComparisonReport(int year1, int year2) {

        // Obter os dados dos dois anos
        Map<Month, BigDecimal> year1TotalsByMonth = getTotalsByMonthForYear(year1);
        Map<Month, BigDecimal> year2TotalsByMonth = getTotalsByMonthForYear(year2);

        // Calcular balanço e porcentagem
        List<MonthlyComparisonDTO> monthlyComparisons = new ArrayList<>();
        for (Month month : Month.values()) {
            BigDecimal year1Total = year1TotalsByMonth.getOrDefault(month, BigDecimal.ZERO);
            BigDecimal year2Total = year2TotalsByMonth.getOrDefault(month, BigDecimal.ZERO);
            BigDecimal balance = year2Total.subtract(year1Total);
            BigDecimal percentage = year1Total.equals(BigDecimal.ZERO) ?
                    BigDecimal.ZERO :
                    balance.multiply(BigDecimal.valueOf(100)).divide(year1Total, RoundingMode.HALF_UP);

            monthlyComparisons.add(new MonthlyComparisonDTO(
                    month.name(),
                    year1Total,
                    year2Total,
                    balance,
                    percentage
            ));
        }

        // Calcular totais anuais
        BigDecimal totalYear1 = year1TotalsByMonth.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalYear2 = year2TotalsByMonth.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalBalance = totalYear2.subtract(totalYear1);
        BigDecimal totalPercentage = totalYear1.equals(BigDecimal.ZERO) ?
                BigDecimal.ZERO :
                totalBalance.multiply(BigDecimal.valueOf(100)).divide(totalYear1, RoundingMode.HALF_UP);

        return new MonthlyComparisonReportDTO(
                monthlyComparisons,
                totalYear1,
                totalYear2,
                totalBalance,
                totalPercentage
        );
    }

    private Map<Month, BigDecimal> getTotalsByMonthForYear(int year) {
        List<OrderWork> closedOrderWorks = orderWorkRepository.findClosedOrderWorksByYear(year);

        // Agrupar OrderWork por mês e calcular os totais para o ano especificado
        return closedOrderWorks.stream()
                .filter(orderWork -> orderWork.getCreateDate().getYear() == year)
                .collect(Collectors.groupingBy(
                        orderWork -> orderWork.getCreateDate().getMonth(),
                        Collectors.reducing(BigDecimal.ZERO, OrderWork::getValueTotal, BigDecimal::add)
                ));
    }
}
