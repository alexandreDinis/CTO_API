package com.dinis.cto.repository;

import com.dinis.cto.model.car.Maintenance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {

    Page<Maintenance> findByStatusTrue(Pageable pageable);

    Page<Maintenance> findByStatusFalse(Pageable pageable);

    @Query("SELECT m FROM Maintenance m WHERE m.status = false AND YEAR(m.createDate) = :year")
    Page<Maintenance> findByStatusFalseAndYear(@Param("year") int year, Pageable pageable);


}
