package com.dinis.cto.repository;

import com.dinis.cto.model.car.Fuel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface FuelRepository extends JpaRepository<Fuel, Long>{

    List<Fuel> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
