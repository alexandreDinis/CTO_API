package com.dinis.cto.repository;

import com.dinis.cto.model.car.ClientCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientCarRepository extends JpaRepository<ClientCar, Long> {

    @Query("SELECT COUNT(c) FROM ClientCar c WHERE FUNCTION('YEAR', c.createDate) = :year AND FUNCTION('MONTH', c.createDate) = :month")
    long countClientCarsByYearAndMonth(@Param("year") Integer year, @Param("month") Integer month);
}
