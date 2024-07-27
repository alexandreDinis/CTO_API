package com.dinis.cto.repository;

import com.dinis.cto.model.os.OrderWork;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface OrderWorkRepository extends JpaRepository<OrderWork,Long> {

    Page<OrderWork> findByStatusTrue(Pageable pageable);

    Page<OrderWork> findByStatusFalse(Pageable pageable);

    @Query("SELECT o FROM OrderWork o WHERE o.status = false AND o.createDate BETWEEN :startDate AND :endDate")
    List<OrderWork> findClosedOrdersWithinPeriod(LocalDate startDate, LocalDate endDate);

    @Query("SELECT COUNT(DISTINCT o.client.id) FROM OrderWork o WHERE FUNCTION('YEAR', o.createDate) = :year AND FUNCTION('MONTH', o.createDate) = :month")
    long countDistinctClientsByYearAndMonth(@Param("year") Integer year, @Param("month") Integer month);

    @Query("SELECT new map(c.fantasyName as fantasyName, SUM(o.valueTotal) as totalValue) " +
            "FROM OrderWork o JOIN o.client c " +
            "WHERE FUNCTION('MONTH', o.createDate) = :month AND FUNCTION('YEAR', o.createDate) = :year " +
            "GROUP BY c.fantasyName " +
            "ORDER BY totalValue DESC")
    List<Map<String, Object>> findTotalValueByClientAndMonth(@Param("year") Integer year, @Param("month") Integer month);

    @Query("SELECT new map(c.fantasyName as fantasyName, SUM(o.valueTotal) as totalValue) " +
            "FROM OrderWork o JOIN o.client c " +
            "WHERE FUNCTION('YEAR', o.createDate) = :year " +
            "GROUP BY c.fantasyName " +
            "ORDER BY totalValue DESC")
    List<Map<String, Object>> findTotalValueByClientAndYear(@Param("year") Integer year);

    @Query("SELECT o FROM OrderWork o JOIN o.works w JOIN w.car c WHERE o.client.id = :clientId AND c.plate = :plate")
    List<OrderWork> findByClientIdAndCarPlate(@Param("clientId") Long clientId, @Param("plate") String plate);

    @Query("SELECT ow FROM OrderWork ow WHERE FUNCTION('YEAR', ow.createDate) = :year AND FUNCTION('MONTH', ow.createDate) = :month")
    List<OrderWork> findOrderWorksByYearAndMonth(@Param("year") Integer year, @Param("month") Integer month);

    @Query("SELECT o FROM OrderWork o WHERE o.status = false AND YEAR(o.createDate) = :year")
    List<OrderWork> findClosedOrderWorksByYear(@Param("year") int year);
}
