package com.dinis.cto.service;


import com.dinis.cto.dto.car.DataFuelDTO;
import com.dinis.cto.model.car.Fuel;
import com.dinis.cto.repository.FuelRepository;
import com.dinis.cto.repository.UserCarRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

            Optional<Fuel> lastFuelOptional = fuelRepository.findFirstByUserCarIdAndStatusTrueOrderByDateDesc(data.carID());

            if (lastFuelOptional.isPresent()) {
                Fuel lastFuel = lastFuelOptional.get();
                var initialKm = car.getInitialKm();

                var fuel = new Fuel(data, car);
                fuel.setStatus(true);
                fuelRepository.save(fuel);
                car.updateTotalKm(data.km());
                userCarRepository.save(car);
                int newkm = car.getInitialKm();
                int kmDifference = newkm - initialKm;
                lastFuel.setKm(kmDifference);
                lastFuel.setStatus(false);
                fuelRepository.save(lastFuel);

            } else {

                var fuel = new Fuel(data, car);
                fuel.setStatus(true); // Novo abastecimento tem status true
                fuelRepository.save(fuel);
                car.addFuel(fuel);
                car.updateTotalKm(data.km());
                userCarRepository.save(car);
            }

        } else {
            throw new EntityNotFoundException("Carro não encontrado: " + data.carID());
        }
    }
}
