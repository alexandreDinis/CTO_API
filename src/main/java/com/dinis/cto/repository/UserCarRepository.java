package com.dinis.cto.repository;


import com.dinis.cto.model.car.UserCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCarRepository extends JpaRepository<UserCar, Long> {

}
