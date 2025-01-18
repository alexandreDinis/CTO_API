package com.dinis.cto.service;

import com.dinis.cto.dto.car.DataCarDTO;
import com.dinis.cto.model.car.UserCar;
import com.dinis.cto.repository.UserCarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    @Autowired
    private UserCarRepository userCarRepository;

    public void registerUserCar(DataCarDTO data) {

        var userCar = new UserCar(data);
        userCarRepository.save(userCar);
    }

    public Page<DataCarDTO> userCarList(Pageable pageable) {
        return userCarRepository.findAll(pageable).map(DataCarDTO::new);
    }
}
