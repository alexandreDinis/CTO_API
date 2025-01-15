package com.dinis.cto.service;

import com.dinis.cto.dto.car.DataMaintenanceDTO;
import com.dinis.cto.dto.car.ExistingMaintenanceDetailsDTO;
import com.dinis.cto.dto.car.ListMaintenanceCarDTO;
import com.dinis.cto.dto.car.MaintenanceDTO;
import com.dinis.cto.model.car.Maintenance;
import com.dinis.cto.model.car.UserCar;
import com.dinis.cto.repository.MaintenanceRepository;
import com.dinis.cto.repository.UserCarRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaintenanceService {

    @Autowired
    private MaintenanceRepository maintenanceRepository;

    @Autowired
    private UserCarRepository userCarRepository;

    public void openMaintenance(DataMaintenanceDTO data) {

        var carOptional = userCarRepository.findById(data.carID());

        if (carOptional.isPresent()) {

            var car = carOptional.get();
            var maintenance = new Maintenance(data);
            maintenance.setUserCar(car);
            maintenanceRepository.save(maintenance);
            car.addMaintenance(maintenance);
            userCarRepository.save(car);

        }
    }

    public void doMaintenance(Long id, ExistingMaintenanceDetailsDTO data) {
        var maintenance = maintenanceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Não há uma manutenção aberta com esse id: " + id));

        maintenance.close();
        maintenanceRepository.save(maintenance);

        var car = maintenance.getUserCar();
        var newMaintenance = new Maintenance(data, maintenance.getDescription(), car);
        maintenanceRepository.save(newMaintenance);

        car.addMaintenance(newMaintenance);
        userCarRepository.save(car);
    }
}
