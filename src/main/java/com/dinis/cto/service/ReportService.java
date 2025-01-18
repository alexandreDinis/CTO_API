package com.dinis.cto.service;

import com.dinis.cto.dto.car.*;
import com.dinis.cto.dto.os.DateRangeDTO;
import com.dinis.cto.dto.os.PaginatedResponseWithTotal;
import com.dinis.cto.dto.os.ResponseOsFalseDTO;
import com.dinis.cto.dto.report.*;
import com.dinis.cto.model.car.Fuel;
import com.dinis.cto.model.car.Maintenance;
import com.dinis.cto.model.car.TypeFuel;
import com.dinis.cto.model.os.BudgetEnum;
import com.dinis.cto.model.os.OrderWork;
import com.dinis.cto.model.os.Parts;
import com.dinis.cto.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
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

    @Autowired
    private MaintenanceRepository maintenanceRepository;

    @Autowired
    private FuelRepository fuelRepository;


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

    public ReportDataDTO generatePartsReport(DateRangeDTO dateRangeDTO) {
        LocalDate startDate = dateRangeDTO.startDate();
        LocalDate endDate = dateRangeDTO.endDate();

        List<OrderWork> orderWorks = orderWorkRepository.findByBudgetAndCreateDateBetween(BudgetEnum.SERVIÇO, startDate, endDate);

        List<Parts> parts = orderWorks.stream()
                .flatMap(orderWork -> orderWork.getWorks().stream())
                .flatMap(work -> work.getParts().stream())
                .toList();

        long quantity = parts.size();
        BigDecimal totalValue = parts.stream()
                .map(Parts::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal averageValue = parts.isEmpty() ? BigDecimal.ZERO : totalValue.divide(new BigDecimal(parts.size()), RoundingMode.HALF_UP);
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
            results = orderWorkRepository.findTotalValueByClientAndMonth(year, month);
        } else {
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

    public MonthlyComparisonReportDTO generateMonthlyComparisonReport(int year1, int year2) {
        Map<Month, BigDecimal> year1TotalsByMonth = getTotalsByMonthForYear(year1);
        Map<Month, BigDecimal> year2TotalsByMonth = getTotalsByMonthForYear(year2);

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

        return closedOrderWorks.stream()
                .filter(orderWork -> orderWork.getCreateDate().getYear() == year)
                .collect(Collectors.groupingBy(
                        orderWork -> orderWork.getCreateDate().getMonth(),
                        Collectors.reducing(BigDecimal.ZERO, OrderWork::getValueTotal, BigDecimal::add)
                ));
    }

    public Page<ListMaintenanceCarDTO> getMaintenancesWithStatusTrue(Pageable pageable) {
        Page<Maintenance> maintenancesPage = maintenanceRepository.findByStatusTrue(pageable);

        return maintenancesPage.map(maintenance -> {
            var car = maintenance.getUserCar();
            List<MaintenanceDTO> maintenanceDTOs = car.getMaintenence().stream()
                    .filter(Maintenance::getStatus)
                    .map(m -> new MaintenanceDTO(
                            m.getId(),
                            m.getDescription(),
                            m.getCreateDate(),
                            m.getValue()
                    ))
                    .collect(Collectors.toList());

            return new ListMaintenanceCarDTO(
                    car.getId(),
                    car.getMake(),
                    car.getModel(),
                    car.getPlate(),
                    car.getInitialKm(),
                    maintenanceDTOs
            );
        });
    }

    public ListMaintenanceReportDTO getMaintenancesWithStatusFalseAndTotal(Integer year, Pageable pageable) {
        Page<Maintenance> maintenancesPage;

        if (year != null) {
            maintenancesPage = maintenanceRepository.findByStatusFalseAndYear(year, pageable);
        } else {
            maintenancesPage = maintenanceRepository.findByStatusFalse(pageable);
        }
        Page<ListMaintenanceCarDTO> maintenanceCarPage = maintenancesPage.map(maintenance -> {
            var car = maintenance.getUserCar();
            List<MaintenanceDTO> maintenanceDTOs = car.getMaintenence().stream()
                    .filter(m -> !m.getStatus()) // status false
                    .map(m -> new MaintenanceDTO(
                            m.getId(),
                            m.getDescription(),
                            m.getCreateDate(),
                            m.getValue()
                    ))
                    .collect(Collectors.toList());

            return new ListMaintenanceCarDTO(
                    car.getId(),
                    car.getMake(),
                    car.getModel(),
                    car.getPlate(),
                    car.getInitialKm(),
                    maintenanceDTOs
            );
        });

        BigDecimal totalCost = maintenanceCarPage.getContent().stream()
                .flatMap(car -> car.maintenances().stream())
                .map(MaintenanceDTO::value)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new ListMaintenanceReportDTO(maintenanceCarPage.getContent(), totalCost);
    }

    public FuelReportTotalsDTO generateFuelReport(DateRangeDTO dateRange, Long userCarId) {
        LocalDate startDate = dateRange.startDate();
        LocalDate endDate = dateRange.endDate();

        String[] types = {TypeFuel.GASOLINA.name(), TypeFuel.ETANOL.name(), TypeFuel.GNV.name(), TypeFuel.DISEL.name()};

        List<FuelReportDTO> reports = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        int totalKm = 0;

        for (String type : types) {
            List<Fuel> fuels = fuelRepository.findByUserCarIdAndDateBetweenAndStatusFalseAndType(
                    userCarId, startDate, endDate, TypeFuel.valueOf(type));

            OptionalDouble averageFuelPriceOpt = fuels.stream()
                    .mapToDouble(fuel -> fuel.getFuelPrice().doubleValue())
                    .average();

            BigDecimal averageFuelPrice = averageFuelPriceOpt.isPresent() ?
                    BigDecimal.valueOf(averageFuelPriceOpt.getAsDouble()).setScale(2, RoundingMode.HALF_UP) :
                    BigDecimal.ZERO;

            int typeTotalKm = fuels.stream()
                    .mapToInt(Fuel::getKm)
                    .sum();

            BigDecimal typeTotalAmount = fuels.stream()
                    .map(Fuel::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal autonomy = BigDecimal.ZERO;
            if (averageFuelPrice.compareTo(BigDecimal.ZERO) > 0 && typeTotalAmount.compareTo(BigDecimal.ZERO) > 0) {
                autonomy = BigDecimal.valueOf(typeTotalKm)
                        .divide(typeTotalAmount.divide(averageFuelPrice, 2, RoundingMode.HALF_UP), 2, RoundingMode.HALF_UP);
            }

            reports.add(new FuelReportDTO(type, averageFuelPrice, typeTotalAmount, typeTotalKm, autonomy));

            totalAmount = totalAmount.add(typeTotalAmount);
            totalKm += typeTotalKm;
        }
        return new FuelReportTotalsDTO(reports, totalAmount, totalKm);
    }
}
