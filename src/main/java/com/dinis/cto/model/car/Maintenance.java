package com.dinis.cto.model.car;

import com.dinis.cto.dto.car.DataMaintenanceDTO;
import com.dinis.cto.dto.car.ExistingMaintenanceDetailsDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Maintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private String local;
    private BigDecimal value;
    private int initKm;
    private int durationKm;
    private Boolean status;
    private LocalDate createDate;

    @ManyToOne
    @JoinColumn(name = "user_car_id", referencedColumnName = "id")
    private UserCar userCar;

    public Maintenance(DataMaintenanceDTO data) {
        this.description = data.description();
        this.local = data.local();
        this.value = data.value();
        this.initKm = data.initKm();
        this.durationKm = data.durationKm();
        this.status = true;
        this.createDate = LocalDate.now();
    }

    public Maintenance(ExistingMaintenanceDetailsDTO data, String description, UserCar userCar) {
        this.description = description;
        this.local = data.local();
        this.value = data.value();
        this.initKm = data.initKm();
        this.durationKm = data.durationKm();
        this.status = true;
        this.createDate = LocalDate.now();
        this.userCar = userCar;
    }

    public void close() {
        this.status = false;
    }
}

