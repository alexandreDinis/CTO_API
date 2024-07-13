package com.dinis.cto.repository;


import com.dinis.cto.model.car.UserCar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCarRepository extends JpaRepository<UserCar, Long> {

}
