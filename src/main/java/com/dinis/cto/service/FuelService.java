package com.dinis.cto.service;


import com.dinis.cto.dto.car.DataFuelDTO;
import com.dinis.cto.model.car.Fuel;
import com.dinis.cto.repository.FuelRepository;
import com.dinis.cto.repository.UserCarRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FuelService {

    @Autowired
    private FuelRepository fuelRepository;

    @Autowired
    private UserCarRepository userCarRepository;

    public void openFuel(DataFuelDTO data) {

        var userCarOptional = userCarRepository.findById(data.carID());

        if (userCarOptional.isPresent()) {

            var car = userCarOptional.get();
            var fuel = new Fuel(data, car);
            fuelRepository.save(fuel);
            car.addFuel(fuel);
            car.updateTotalKm(data.km());
            userCarRepository.save(car);
        } else {
            throw new EntityNotFoundException("Carro não encontrado" + data.carID());
        }
    }

    //Todo: criar lista de abastecimento por combustivel e total por mes e geral
}
