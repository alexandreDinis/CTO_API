package com.dinis.cto.model.car;


import com.dinis.cto.dto.car.DataFuelDTO;
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
public class Fuel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_car_id")
    private UserCar userCar;

    @Enumerated(EnumType.STRING)
    private TypeFuel type;

    private BigDecimal fuelPrice;

    private BigDecimal amount;

    private int km;

    private LocalDate date;

    private boolean status;

    public Fuel(DataFuelDTO data, UserCar userCar) {
        this.userCar = userCar;
        this.type = data.typeFuel();
        this.fuelPrice = data.fuelPrice();
        this.amount = data.amount();
        this.km = data.km();
        this.date = LocalDate.now();
    }
}
