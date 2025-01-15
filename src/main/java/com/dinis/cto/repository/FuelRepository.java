package com.dinis.cto.repository;

import com.dinis.cto.model.car.Fuel;

import com.dinis.cto.model.car.TypeFuel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FuelRepository extends JpaRepository<Fuel, Long>{

    @Query("SELECT f FROM Fuel f JOIN f.userCar uc WHERE f.date BETWEEN :startDate AND :endDate AND f.status = false AND uc.id = :userCarId")
    List<Fuel> findByDateBetweenAndStatusFalseAndUserCarId(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("userCarId") Long userCarId);

    Optional<Fuel> findFirstByUserCarIdAndStatusTrueOrderByDateDesc(Long userCarId);

    List<Fuel> findByUserCarIdAndDateBetweenAndStatusFalse(Long userCarId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT f FROM Fuel f WHERE f.userCar.id = :userCarId AND f.date BETWEEN :startDate AND :endDate AND f.status = false AND f.type = :type")
    List<Fuel> findByUserCarIdAndDateBetweenAndStatusFalseAndType(
            @Param("userCarId") Long userCarId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("type") TypeFuel type
    );

}
