package com.dinis.cto.repository;

import com.dinis.cto.model.car.ClientCar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientCarRepository extends JpaRepository<ClientCar, Long> {
}
